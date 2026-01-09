package com.andrewpg.springboottemplate.exception.base;

import lombok.Getter;

/**
 * Excepción de negocio base. Úsala para errores de lógica de negocio que NO necesitan auditarse
 * (validaciones, reglas de negocio simples, etc.)
 */
@Getter
public class BusinessException extends RuntimeException implements AuditableException {

  private final ErrorSeverity severity;
  private final String errorCode;

  public BusinessException(String message) {
    this(message, ErrorSeverity.WARNING, null);
  }

  public BusinessException(String message, ErrorSeverity severity) {
    this(message, severity, null);
  }

  public BusinessException(String message, ErrorSeverity severity, String errorCode) {
    super(message);
    this.severity = severity;
    this.errorCode = errorCode;
  }

  @Override
  public boolean shouldAudit() {
    // Las excepciones de negocio solo se auditan si son ERROR o FATAL
    return severity == ErrorSeverity.ERROR || severity == ErrorSeverity.FATAL;
  }
}
