# Use a lightweight OpenJDK 17 base image
FROM openjdk:21-jdk-slim

# Copy the jar file from target directory to the container
COPY target/adhunnikkethi-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 for Spring Boot app
EXPOSE 8080

# Command to run the jar file
ENTRYPOINT ["java", "-jar", "/app.jar"]
