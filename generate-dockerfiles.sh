#!/bin/bash

# Script to generate Dockerfiles for all remaining services
# This creates consistent Dockerfiles for inventory, order, rental, notification, report, and auth services

SERVICES=("inventory-service" "order-service" "rental-service" "notification-service" "report-service" "auth-service")
PORTS=("8083" "8084" "8085" "8086" "8087" "8088")

for i in "${!SERVICES[@]}"; do
    SERVICE=${SERVICES[$i]}
    PORT=${PORTS[$i]}
    
    echo "Creating Dockerfile for $SERVICE..."
    
    cat > $SERVICE/Dockerfile << EOF
# Multi-stage build for $SERVICE
FROM maven:3.9-openjdk-17 AS build

WORKDIR /app

# Copy parent POM and shared dependencies first (for better caching)
COPY pom.xml .
COPY shared-libs/ ./shared-libs/

# Copy $SERVICE specific files
COPY $SERVICE/pom.xml ./$SERVICE/
COPY $SERVICE/src ./$SERVICE/src

# Build the application
RUN mvn clean package -pl $SERVICE -am -DskipTests

# Runtime stage
FROM openjdk:17-jre-slim

WORKDIR /app

# Install curl for health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Create non-root user
RUN groupadd -r bookstore && useradd -r -g bookstore bookstore

# Copy the built JAR
COPY --from=build /app/$SERVICE/target/*.jar app.jar

# Change ownership to non-root user
RUN chown -R bookstore:bookstore /app

USER bookstore

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \\
  CMD curl -f http://localhost:$PORT/actuator/health || exit 1

EXPOSE $PORT

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
EOF

done

echo "All Dockerfiles created successfully!"
