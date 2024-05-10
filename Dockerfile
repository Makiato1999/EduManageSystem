# Use a base image with Java, for example, the OpenJDK 11 slim version
FROM openjdk:23-ea-17-jdk-bullseye

# Optionally set a maintainer label to identify the creator of the built images
LABEL maintainer="xiexiaoran"

# Copy the JAR file from your local machine's target directory to the Docker image
COPY target/EduManageSystem.jar /app/EduManageSystem.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/EduManageSystem.jar"]