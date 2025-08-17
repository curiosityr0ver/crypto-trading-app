# Use a base image with a compatible version of Java.
FROM eclipse-temurin:17-jdk-focal

# Set the working directory inside the container.
WORKDIR /app

# Copy the Maven project files. This allows Docker to cache the dependencies.
COPY pom.xml .
COPY src ./src

# Build the Spring Boot application, skipping tests.
# This creates a JAR file in the 'target' directory.
RUN apt-get update && apt-get install -y maven
RUN mvn clean install -DskipTests

# Copy the generated JAR file into the container.
# The JAR is typically named 'your-app-name-version.jar'.
# You may need to replace the wildcard with your actual JAR name.
COPY target/*.jar app.jar

# Expose the port on which the application will run.
EXPOSE 8080

# Define the command to run the application when the container starts.
ENTRYPOINT ["java", "-jar", "app.jar"]