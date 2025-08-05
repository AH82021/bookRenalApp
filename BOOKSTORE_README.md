# 📚 Book Store & Rental Microservices Platform - PROJECT SKELETON CREATED

A scalable, cloud-native backend architecture for a modern Book Store & Book Rental application. Built with **Java 17+, Spring Boot 3.x, AWS**, and a microservices-first approach supporting both synchronous (REST) and asynchronous (event-driven) communication.

> **🎉 PROJECT SKELETON SUCCESSFULLY CREATED!**
>
> The complete microservices architecture has been set up with all the necessary services, configurations, and development infrastructure.

---

## **✅ What's Been Created**

### **📁 Project Structure**

```
bookstore-platform/
│
├── 🚪 api-gateway/           # Spring Cloud Gateway (✅ CREATED)
├── 👥 user-service/          # User management (✅ CREATED)
├── 📖 book-service/          # Book catalog (✅ CREATED)
├── 📦 inventory-service/     # Stock management (✅ CREATED)
├── 🛒 order-service/         # Orders & purchases (✅ CREATED)
├── 📅 rental-service/        # Rental lifecycle (✅ CREATED)
├── 📧 notification-service/  # Email/SMS notifications (✅ CREATED)
├── 📊 report-service/        # Analytics & reports (✅ CREATED)
├── 🔐 auth-service/          # Authentication (✅ CREATED)
├── 🔗 shared-libs/           # Common DTOs & events (✅ CREATED)
├── 🐳 deploy/                # Deployment configs (✅ CREATED)
├── 🐳 docker-compose.yml     # Local dev infrastructure (✅ CREATED)
├── 🛠️ setup-dev.sh           # Development setup script (✅ CREATED)
├── 📋 pom.xml                # Multi-module Maven project (✅ CREATED)
└── 🔧 .mvn/wrapper/          # Maven wrapper (✅ CREATED)
```

### **🛠️ Infrastructure & Configuration**

- ✅ **Multi-module Maven project** with parent POM
- ✅ **Docker Compose** for local development with all databases and services
- ✅ **Maven wrapper** for consistent builds
- ✅ **Spring Boot 3.x & Spring Cloud** dependencies configured
- ✅ **Database per service** pattern with PostgreSQL
- ✅ **Redis** for caching (inventory service)
- ✅ **RabbitMQ** for async messaging
- ✅ **Eureka** service discovery
- ✅ **LocalStack** for AWS services emulation

### **📋 Services Configuration**

| Service           | Port | Database                | Status        |
| ----------------- | ---- | ----------------------- | ------------- |
| API Gateway       | 8080 | -                       | ✅ Configured |
| User Service      | 8081 | PostgreSQL:5432         | ✅ Configured |
| Book Service      | 8082 | PostgreSQL:5433         | ✅ Configured |
| Inventory Service | 8083 | PostgreSQL:5434 + Redis | ✅ Configured |
| Order Service     | 8084 | PostgreSQL:5435         | ✅ Configured |
| Rental Service    | 8085 | PostgreSQL:5436         | ✅ Configured |
| Report Service    | 8086 | PostgreSQL:5437         | ✅ Configured |
| Auth Service      | 8087 | Redis                   | ✅ Configured |

---

## **🚀 Quick Start Guide**

### **1. Prerequisites**

- ✅ Java 17+
- ✅ Docker & Docker Compose
- ✅ Maven (or use included wrapper)

### **2. Start Development Environment**

```bash
# Make setup script executable and run it
chmod +x setup-dev.sh
./setup-dev.sh
```

This will:

- 🐳 Start all infrastructure services (databases, Redis, RabbitMQ, Eureka)
- 🏗️ Build all microservices
- ⏳ Verify all services are ready

### **3. Start Individual Services**

```bash
# Terminal 1: API Gateway
cd api-gateway && ./mvnw spring-boot:run

# Terminal 2: User Service
cd user-service && ./mvnw spring-boot:run

# Terminal 3: Book Service
cd book-service && ./mvnw spring-boot:run

# Continue for other services...
```

### **4. Verify Setup**

- 🌐 **API Gateway**: http://localhost:8080
- 🔍 **Eureka Dashboard**: http://localhost:8761
- 🐰 **RabbitMQ Management**: http://localhost:15672 (user: bookstore, pass: password)

---

## **🧬 Architecture Overview**

- **Microservices:** Each core business domain is encapsulated in its own Spring Boot service.
- **API Gateway:** All client and external API traffic flows through a unified gateway (Spring Cloud Gateway).
- **Service-to-Service Communication:** Uses both REST (synchronous) and message queues (asynchronous, e.g., RabbitMQ).
- **Polyglot Persistence:** Each service owns its own database.
- **Cloud Native:** Ready for containerization and AWS deployment.

---

## **🔄 Communication Flow**

### **Synchronous (REST):**

- **Clients** interact with services via **API Gateway** using HTTP/JSON APIs.
- **Services** talk to each other directly for real-time needs (e.g., Order → Inventory).

### **Asynchronous (Events/Queues):**

- Major business events (order placed, book rented/returned, etc.) are **published to RabbitMQ**.
- **Notification Service** listens to these events to trigger emails/SMS.
- **Report Service** consumes events for analytics.

---

## **📊 Example Service Flow: Book Rental**

1. **User** logs in (User Service) and searches for books (Book Service).
2. **User** requests to rent a book (Rental Service via API Gateway).
3. **Rental Service** checks availability by calling Inventory Service (REST).
4. If available, Rental Service places a hold and creates a rental order.
5. **Rental Service** publishes a `RENTAL_CREATED` event to RabbitMQ.
6. **Notification Service** receives the event, sends a confirmation email/SMS.
7. **Report Service** updates analytics based on the event.

---

## **🗄️ Database Configuration**

Each service has its own PostgreSQL database:

| Service   | Database    | Port | Connection                 |
| --------- | ----------- | ---- | -------------------------- |
| User      | userdb      | 5432 | localhost:5432/userdb      |
| Book      | bookdb      | 5433 | localhost:5433/bookdb      |
| Inventory | inventorydb | 5434 | localhost:5434/inventorydb |
| Order     | orderdb     | 5435 | localhost:5435/orderdb     |
| Rental    | rentaldb    | 5436 | localhost:5436/rentaldb    |
| Report    | reportdb    | 5437 | localhost:5437/reportdb    |

**Credentials:** username: `bookstore_user`, password: `password`

---

## **📦 What's Included**

### **✅ Infrastructure**

- Docker Compose with all required services
- Service discovery with Eureka
- Message queuing with RabbitMQ
- Caching with Redis
- Local AWS services with LocalStack

### **✅ Microservices**

- Complete Spring Boot applications for each service
- Proper dependency management with Maven
- Service-specific configurations
- Database connectivity setup

### **✅ Shared Libraries**

- Common DTOs for inter-service communication
- Event objects for async messaging
- Utility classes and common configurations

### **✅ Development Tools**

- Maven wrapper for consistent builds
- Development setup script
- Docker configurations
- Deployment examples

---

## **🚀 Deployment**

The `/deploy` directory contains configurations for:

- **Docker:** Individual Dockerfiles for each service
- **Kubernetes:** Deployment manifests and services
- **AWS ECS:** Task definitions and service configurations
- **Infrastructure as Code:** Terraform and AWS CDK examples

---

## **📋 Next Steps**

### **Immediate Development Tasks:**

1. **Implement Controllers, Services, and Repositories** for each microservice
2. **Add API documentation** with OpenAPI/Swagger
3. **Implement business logic** for book rental workflows
4. **Add security** with JWT authentication
5. **Write unit and integration tests**

### **Advanced Features:**

1. **Distributed tracing** with Sleuth/Zipkin
2. **Monitoring** with Prometheus and Grafana
3. **Circuit breakers** with Resilience4j
4. **API rate limiting** and throttling
5. **Event sourcing** for audit trails

### **Production Readiness:**

1. **Containerization** with Docker
2. **Orchestration** with Kubernetes or AWS ECS
3. **CI/CD pipelines** with GitHub Actions
4. **Security hardening** and secret management
5. **Performance optimization** and scaling

---

## **🛠️ Development Commands**

```bash
# Build entire project
./mvnw clean compile

# Build specific service
cd user-service && ./mvnw clean package

# Run tests
./mvnw test

# Start infrastructure only
docker-compose up -d

# Stop all containers
docker-compose down

# Clean rebuild
./mvnw clean install
```

---

## **🔧 Troubleshooting**

### **Common Issues:**

1. **Port conflicts:** Check if ports 5432-5437, 6379, 5672, 8761, 8080-8087 are available
2. **Docker not running:** Ensure Docker Desktop is started
3. **Memory issues:** Increase Docker memory allocation
4. **Database connection:** Wait for containers to fully start (30-60 seconds)

### **Logs:**

```bash
# View container logs
docker-compose logs [service-name]

# View application logs
tail -f [service]/logs/application.log
```

---

## **🎯 Project Status: SKELETON COMPLETE ✅**

The complete microservices architecture skeleton has been successfully created! You now have:

- 🏗️ **9 microservices** ready for development
- 🐳 **Complete infrastructure** setup with Docker
- 🔧 **Development tools** and scripts
- 📚 **Documentation** and examples
- 🚀 **Deployment configurations**

**Ready to start implementing business logic!** 🚀

# ...repeat for other services

# 3. Start API Gateway

cd api-gateway && ./mvnw spring-boot:run

````

---

## **Deployment**

- **AWS ECS/EKS:** Each microservice is containerized and deployed as an independent task/pod.
- **Database:** Each service manages its own DB (Postgres/Redis), ideally on AWS RDS/Elasticache.
- **S3:** Book images, files.
- **SQS/Kafka:** For events and async flows.
- **CI/CD:** GitHub Actions, Jenkins, or AWS CodePipeline.

---

## **Best Practices**

- **Domain-driven design**: Each microservice encapsulates a core domain.
- **Single Responsibility Principle:** Keep logic focused in each service.
- **API First:** OpenAPI (Swagger) for all REST endpoints.
- **Observability:** Centralized logging (CloudWatch), distributed tracing (Sleuth/Zipkin).
- **Security:** OAuth2/JWT, role-based access, encrypt secrets at rest.
- **Scalability:** Stateless design, autoscaling on AWS.

---

## **Diagrams**

### **System Overview**
```mermaid
graph TD
    API_GW[API Gateway] --> UserS[User Service]
    API_GW --> BookS[Book Service]
    API_GW --> InventoryS[Inventory Service]
    API_GW --> OrderS[Order Service]
    API_GW --> RentalS[Rental Service]
    API_GW --> ReportS[Report Service]
    API_GW --> AuthS[Auth Service]
    OrderS -- Event --> SQS[SQS/Kafka]
    RentalS -- Event --> SQS
    SQS --> NotificationS[Notification Service]
    SQS --> ReportS
    BookS --> S3
````

---

## **Contributing**

1. Fork the repo & clone.
2. Set up `.env` or config files as needed.
3. Follow [conventional commits](https://www.conventionalcommits.org/) for PRs.
4. Write unit & integration tests (see `/shared-libs/test-utils`).
5. Open a PR—describe changes and reference relevant issues.

---

## **License**

MIT

---

## **Contact**

Maintainer: [Your Name]  
Email: [your.email@example.com]  
Slack: #bookstore-backend

---

**Questions?**  
Open an [issue](https://github.com/your-org/bookstore-platform/issues) or start a discussion!
