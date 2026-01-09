#!/bin/bash
set -e

# Compilar inicialmente
./gradlew compileJava

# Ejecutar compilación continua en background
./gradlew compileJava --continuous &
GRADLE_PID=$!

# Función para limpiar procesos al salir
cleanup() {
    echo "Deteniendo procesos..."
    kill $GRADLE_PID 2>/dev/null || true
    exit 0
}

# Capturar señales para limpiar
trap cleanup SIGTERM SIGINT

# Ejecutar bootRun en foreground
./gradlew bootRun -Dspring.devtools.restart.enabled=true

# Limpiar al terminar
cleanup

