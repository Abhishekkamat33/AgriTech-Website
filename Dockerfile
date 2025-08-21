# Stage 1: Build with Maven and Java 17 (Temurin)
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run with Eclipse Temurin Java 21 runtime
FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY --from=build /app/target/adhunnikkethi-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
