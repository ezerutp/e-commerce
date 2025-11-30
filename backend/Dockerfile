# Etapa 1: Construcción
FROM gradle:8.5-jdk21 AS build
WORKDIR /app

# Copiar archivos de configuración de Gradle
COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle

# Copiar el código fuente
COPY src src

# Construir la aplicación
RUN gradle build -x test --no-daemon

# Etapa 2: Ejecución
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copiar el JAR desde la etapa de construcción
COPY --from=build /app/build/libs/*.jar app.jar

# Exponer el puerto
EXPOSE 8080

# Variables de entorno por defecto (se pueden sobrescribir en docker-compose)
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
