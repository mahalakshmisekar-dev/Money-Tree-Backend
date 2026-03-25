# Use OpenJDK 17 base image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory inside container
WORKDIR /app

# Copy Maven project files
COPY pom.xml .
COPY src ./src

# Build the project
RUN ./mvnw clean package -DskipTests

# Expose port 8080
EXPOSE 8085

# Run the Spring Boot app
CMD ["java", "-jar", "target/moneytree-backend-0.0.1-SNAPSHOT.jar"]