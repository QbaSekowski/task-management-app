FROM openjdk:17-jdk-slim
WORKDIR application
COPY target/task-management-app.jar ./
ENTRYPOINT ["java", "-jar","task-management-app.jar"]