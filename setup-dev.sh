#!/bin/bash

# Book Store & Rental Platform - Development Setup Script

echo "🚀 Setting up Book Store & Rental Platform for development..."

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker is not running. Please start Docker and run this script again."
    exit 1
fi

# Start infrastructure services
echo "📦 Starting infrastructure services (databases, messaging, service discovery)..."
docker-compose up -d

# Wait for services to be ready
echo "⏳ Waiting for services to be ready..."
sleep 30

# Function to check if a service is ready
check_service() {
    local service_name=$1
    local port=$2
    local max_attempts=30
    local attempt=1

    while [ $attempt -le $max_attempts ]; do
        if nc -z localhost $port 2>/dev/null; then
            echo "✅ $service_name is ready"
            return 0
        fi
        echo "⏳ Waiting for $service_name... (attempt $attempt/$max_attempts)"
        sleep 2
        ((attempt++))
    done

    echo "❌ $service_name failed to start"
    return 1
}

# Check if services are ready
check_service "PostgreSQL (User DB)" 5432
check_service "PostgreSQL (Book DB)" 5433
check_service "PostgreSQL (Inventory DB)" 5434
check_service "PostgreSQL (Order DB)" 5435
check_service "PostgreSQL (Rental DB)" 5436
check_service "PostgreSQL (Report DB)" 5437
check_service "Redis" 6379
check_service "RabbitMQ" 5672
check_service "Eureka Server" 8761

echo "🏗️ Building all microservices..."
mvn clean compile -DskipTests

echo "✅ Development environment is ready!"
echo ""
echo "📋 Next steps:"
echo "1. Start individual services:"
echo "   cd api-gateway && ./mvnw spring-boot:run"
echo "   cd user-service && ./mvnw spring-boot:run"
echo "   cd book-service && ./mvnw spring-boot:run"
echo "   # ... repeat for other services"
echo ""
echo "2. Access services:"
echo "   - API Gateway: http://localhost:8080"
echo "   - Eureka Dashboard: http://localhost:8761"
echo "   - RabbitMQ Management: http://localhost:15672 (user: bookstore, pass: password)"
echo ""
echo "3. Database connections:"
echo "   - User DB: localhost:5432/userdb"
echo "   - Book DB: localhost:5433/bookdb"
echo "   - Inventory DB: localhost:5434/inventorydb"
echo "   - Order DB: localhost:5435/orderdb"
echo "   - Rental DB: localhost:5436/rentaldb"
echo "   - Report DB: localhost:5437/reportdb"
echo "   - Username: bookstore_user, Password: password"
