FROM openjdk:17-jdk-alpine

WORKDIR /appMicroservicioUsuario2

COPY target/microservicio-usuario-0.0.1-SNAPSHOT.jar mServiceUsuario.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "mServiceUsuario.jar"]



