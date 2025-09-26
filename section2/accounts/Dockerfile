# Stage 1: Build with Maven
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean install -DskipTests

# Stage 2: Run the jar
FROM openjdk:17-jdk-slim
WORKDIR /app

LABEL "org.opencontainers.image.authors"="serahawi"

COPY --from=builder /app/target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","accounts-0.0.1-SNAPSHOT.jar"]
