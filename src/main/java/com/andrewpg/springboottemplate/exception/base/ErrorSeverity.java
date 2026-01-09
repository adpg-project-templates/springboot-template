package com.andrewpg.springboottemplate.exception.base;

public enum ErrorSeverity {
  INFO, // Errores informativos, no críticos (ej: validación)
  WARNING, // Advertencias, pueden necesitar atención
  ERROR, // Errores que requieren atención
  FATAL // Errores críticos que siempre se auditan
}
