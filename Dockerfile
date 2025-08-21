# Stage 1: Build with Maven and Eclipse Temurin JDK 17
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and download dependencies to speed up build
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy sources and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run with Eclipse Temurin JDK 21
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy JAR from build stage
COPY --from=build /app/target/adhunnikkethi-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
