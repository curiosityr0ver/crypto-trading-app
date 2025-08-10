# -------- BUILD STAGE --------
FROM maven:3.8.8-eclipse-temurin-17 AS builder
WORKDIR /app

# Cache dependencies
COPY pom.xml .
RUN mvn -B -DskipTests dependency:go-offline

# Copy source and build
COPY src ./src
RUN mvn -B -DskipTests package

# -------- RUNTIME STAGE --------
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy built jar
COPY --from=builder /app/target/*.jar app.jar

# Optional: JVM options
ENV JAVA_OPTS="-Xms256m -Xmx512m -Djava.security.egd=file:/dev/./urandom"

# Expose port
EXPOSE 8080

# Run app
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app.jar"]
