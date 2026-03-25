# Use Maven + JDK 17 for building the app
FROM maven:3.9.2-eclipse-temurin-17-alpine AS build

WORKDIR /app

# Copy project files
COPY pom.xml .
COPY src ./src

# Build the project and skip tests for faster builds
RUN mvn clean package -DskipTests

# Use a lightweight JDK image for running
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (use 8080 or Render’s port if needed)
EXPOSE 8085

# Run the jar
CMD ["java", "-jar", "app.jar"]