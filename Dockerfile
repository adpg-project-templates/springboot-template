FROM gradle:7.6-jdk17
WORKDIR /app

# Copiar los archivos necesarios
COPY build.gradle settings.gradle gradlew gradlew.bat /app/
COPY gradle /app/gradle
COPY src /app/src

# Ejecutar directamente con bootRun para hot-reload
CMD ["./gradlew", "bootRun", "-Dspring.devtools.restart.enabled=true"]
