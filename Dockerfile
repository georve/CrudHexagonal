FROM openjdk:21-slim
LABEL authors="georman"

copy ../target/*.jar app.jar

ENTRYPOINT ["java", "-jar","/app.jar"]