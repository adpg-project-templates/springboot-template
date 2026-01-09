FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copiar los archivos necesarios
COPY build.gradle settings.gradle gradlew gradlew.bat /app/
COPY gradle /app/gradle
COPY src /app/src

# Copiar y dar permisos al script de entrada
COPY docker-entrypoint.sh /app/
RUN chmod +x gradlew docker-entrypoint.sh

# Usar el script de entrada para compilación continua y bootRun
ENTRYPOINT ["./docker-entrypoint.sh"]
