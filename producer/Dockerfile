FROM openjdk:11-jdk-slim

ARG JAR_FILE=target/producer-*.jar
WORKDIR /app
COPY ${JAR_FILE} producer-app.jar

ENTRYPOINT ["java", "-jar", "producer-app.jar"]