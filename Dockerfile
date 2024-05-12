FROM maven:3.9.6-eclipse-temurin-21 AS builder

# Copy the project files into the working directory.
COPY pom.xml .
COPY src ./src

RUN mvn clean package

FROM openjdk:21-jdk

# Set the working directory in the container.
WORKDIR /app

# Copy the JAR file from the builder stage to the container.
COPY --from=builder target/*.jar /app/w2m.jar

# Expose port 3000 for the Spring Boot application.
EXPOSE 3000

# Run the Spring Boot application when the container starts.
CMD ["java", "-jar", "w2m.jar"]
