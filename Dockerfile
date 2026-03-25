FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
COPY src ./src

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

EXPOSE 8085
CMD ["java", "-jar", "target/moneytree-backend-0.0.1-SNAPSHOT.jar"]