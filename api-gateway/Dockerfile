# Multi-stage build for API Gateway
FROM maven:3.9-openjdk-17 AS build

WORKDIR /app

# Copy parent POM and shared dependencies first (for better caching)
COPY pom.xml .
COPY shared-libs/ ./shared-libs/

# Copy API Gateway specific files
COPY api-gateway/pom.xml ./api-gateway/
COPY api-gateway/src ./api-gateway/src

# Build the application
RUN mvn clean package -pl api-gateway -am -DskipTests

# Runtime stage
FROM openjdk:17-jre-slim

WORKDIR /app

# Create non-root user
RUN groupadd -r bookstore && useradd -r -g bookstore bookstore

# Copy the built JAR
COPY --from=build /app/api-gateway/target/*.jar app.jar

# Change ownership to non-root user
RUN chown -R bookstore:bookstore /app

USER bookstore

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

EXPOSE 8080

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
