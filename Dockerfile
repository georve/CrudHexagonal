FROM openjdk:21-slim
LABEL authors="georman"

COPY wait-for-it.sh /wait-for-it.sh

RUN chmod +x /wait-for-it.sh
copy ../target/*.jar app.jar

ENTRYPOINT ["/wait-for-it.sh", "localhost:8080", "--", "java", "-jar", "/app.jar"]
EXPOSE 8080
HEALTHCHECK CMD curl --fail localhost:8080/actuator/health || exit 1