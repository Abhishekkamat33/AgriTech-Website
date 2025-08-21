# Use Maven with Eclipse Temurin 17 for build stage
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Use Eclipse Temurin 21 JDK to run the app
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/adhunnikkethi-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
