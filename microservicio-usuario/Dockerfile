# Usa la imagen base de AWS Lambda para Java 17
FROM public.ecr.aws/lambda/java:17

WORKDIR /appMicroservicioUsuario2

COPY target/microservicio-usuario-0.0.1-SNAPSHOT.jar mServiceUsuario.jar

# Configurar el comando que Lambda ejecutará para tu función
CMD ["java", "-jar", "mServiceUsuario.jar"]

