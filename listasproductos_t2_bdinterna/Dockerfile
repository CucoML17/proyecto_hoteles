FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/ListasProductos_T2-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_listas_productos.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_listas_productos.jar"]