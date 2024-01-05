# Base image with Java 17 for compatibility with Spring Boot 3.1.0
FROM openjdk:17-alpine

# Maintainer information (optional but recommended)
LABEL maintainer="Nishi Dixit"

# Working directory inside the container
WORKDIR /app

# Copy the compiled JAR file and any static assets
COPY build/libs/PayMe-0.0.1-SNAPSHOT.jar payme.jar

# Expose port 8080 (or the port your application listens on)
EXPOSE 8080

# Run the application with optional arguments
ENTRYPOINT ["java", "-jar", "payme.jar"]
