# Usamos una imagen base con JDK 17
FROM eclipse-temurin:17-jdk-focal

# Directorio de trabajo en el contenedor
WORKDIR /app

# Copiamos el archivo JAR de la aplicación
COPY build/libs/*.jar app.jar

# Exponemos el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java","-jar","/app/app.jar"]