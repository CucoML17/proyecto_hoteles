FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/ProyectoHoteles-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_hoteles_proyecto.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_hoteles_proyecto.jar"]