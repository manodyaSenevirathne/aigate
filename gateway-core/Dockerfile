FROM eclipse-temurin:17-jdk-alpine
LABEL authors="astro"
WORKDIR /app
COPY build/libs/app.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]