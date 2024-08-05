FROM openjdk:17-jdk-slim
LABEL authors="HLC"
WORKDIR /app
COPY target/application.jar /app/application.jar
ENTRYPOINT ["java", "-jar","application.jar", "--spring.profiles.active=docker"]
