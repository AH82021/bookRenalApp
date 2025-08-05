# Multi-stage build for order-service
FROM maven:3.9-openjdk-17 AS build

WORKDIR /app

# Copy parent POM and shared dependencies first (for better caching)
COPY pom.xml .
COPY shared-libs/ ./shared-libs/

# Copy order-service specific files
COPY order-service/pom.xml ./order-service/
COPY order-service/src ./order-service/src

# Build the application
RUN mvn clean package -pl order-service -am -DskipTests

# Runtime stage
FROM openjdk:17-jre-slim

WORKDIR /app

# Install curl for health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Create non-root user
RUN groupadd -r bookstore && useradd -r -g bookstore bookstore

# Copy the built JAR
COPY --from=build /app/order-service/target/*.jar app.jar

# Change ownership to non-root user
RUN chown -R bookstore:bookstore /app

USER bookstore

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8084/actuator/health || exit 1

EXPOSE 8084

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
