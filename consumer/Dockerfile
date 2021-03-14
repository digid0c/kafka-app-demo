FROM openjdk:11-jdk-slim

ARG JAR_FILE=target/consumer-*.jar
WORKDIR /app
COPY ${JAR_FILE} consumer-app.jar

ENTRYPOINT ["java", "-jar", "consumer-app.jar"]