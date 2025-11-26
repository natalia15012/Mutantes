# ========================================
# ETAPA 1: BUILD (Compilación)
# ========================================
FROM alpine:latest as build

# Instalamos OpenJDK 17 y dos2unix
RUN apk update && apk add openjdk17 dos2unix

# Copiar TODO el código fuente
COPY . .

# Convertir gradlew a formato Linux y dar permisos
RUN dos2unix gradlew && chmod +x ./gradlew

# Compilar
RUN ./gradlew bootJar --no-daemon

# ========================================
# ETAPA 2: RUNTIME (Ejecución)
# ========================================
FROM eclipse-temurin:17-jdk-alpine

EXPOSE 8080


# Usamos el asterisco (*) para copiar CUALQUIER .jar que se haya generado.
# Así no importa si se llama "Mutantes-0.0.1" o "Examen-1.0", funcionará igual.
COPY --from=build /build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]