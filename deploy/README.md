# Deployment Configurations

This directory contains deployment configurations for the Book Store & Rental Platform.

## Docker Configurations

### Individual Service Dockerfiles

Each microservice will have its own Dockerfile for containerization:

```dockerfile
# Example Dockerfile for any Spring Boot service
FROM openjdk:17-jre-slim

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Multi-stage Build Example

```dockerfile
# Multi-stage build for production
FROM maven:3.9-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jre-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

## Kubernetes Configurations

### Namespace

```yaml
apiVersion: v1
kind: Namespace
metadata:
  name: bookstore
```

### Example Service Deployment

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: user-service
  namespace: bookstore
spec:
  replicas: 2
  selector:
    matchLabels:
      app: user-service
  template:
    metadata:
      labels:
        app: user-service
    spec:
      containers:
        - name: user-service
          image: bookstore/user-service:latest
          ports:
            - containerPort: 8081
          env:
            - name: SPRING_PROFILES_ACTIVE
              value: "k8s"
            - name: EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE
              value: "http://eureka-service:8761/eureka/"
```

## AWS ECS Configurations

### Task Definition Example

```json
{
  "family": "user-service-task",
  "networkMode": "awsvpc",
  "requiresCompatibilities": ["FARGATE"],
  "cpu": "256",
  "memory": "512",
  "executionRoleArn": "arn:aws:iam::ACCOUNT:role/ecsTaskExecutionRole",
  "containerDefinitions": [
    {
      "name": "user-service",
      "image": "ACCOUNT.dkr.ecr.REGION.amazonaws.com/bookstore/user-service:latest",
      "portMappings": [
        {
          "containerPort": 8081,
          "protocol": "tcp"
        }
      ],
      "environment": [
        {
          "name": "SPRING_PROFILES_ACTIVE",
          "value": "aws"
        }
      ],
      "logConfiguration": {
        "logDriver": "awslogs",
        "options": {
          "awslogs-group": "/ecs/user-service",
          "awslogs-region": "us-west-2",
          "awslogs-stream-prefix": "ecs"
        }
      }
    }
  ]
}
```

## Environment-specific Configurations

### application-dev.yml

For development environment

### application-staging.yml

For staging environment

### application-prod.yml

For production environment

## Infrastructure as Code

### Terraform

- VPC and networking setup
- RDS database instances
- ElastiCache for Redis
- SQS queues
- S3 buckets
- Load balancers

### AWS CDK

Alternative to Terraform using TypeScript/Python

## CI/CD Pipeline

### GitHub Actions Example

```yaml
name: Build and Deploy

on:
  push:
    branches: [main]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: "17"
          distribution: "temurin"
      - name: Build with Maven
        run: mvn clean package
      - name: Build Docker image
        run: docker build -t bookstore/user-service:${{ github.sha }} ./user-service
      - name: Push to ECR
        # ECR push commands
```

## Monitoring and Observability

### Prometheus Configuration

### Grafana Dashboards

### ELK Stack Setup

### Distributed Tracing with Jaeger

## Security

### Secrets Management

- AWS Secrets Manager
- Kubernetes Secrets
- Environment variable injection

### Network Security

- Security groups
- NACLs
- VPC configuration
