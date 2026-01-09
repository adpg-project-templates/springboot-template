package com.andrewpg.springboottemplate.exception.base;

import lombok.Getter;

/**
 * Excepción crítica que SIEMPRE se audita. Úsala para errores graves del sistema (fallos de BD,
 * servicios externos caídos, problemas de seguridad, etc.)
 */
@Getter
public class CriticalException extends RuntimeException implements AuditableException {

  private final String errorCode;

  public CriticalException(String message, String errorCode) {
    super(message);
    this.errorCode = errorCode;
  }

  public CriticalException(String message, Throwable cause) {
    this(message, null, cause);
  }

  public CriticalException(String message, String errorCode, Throwable cause) {
    super(message, cause);
    this.errorCode = errorCode;
  }

  @Override
  public ErrorSeverity getSeverity() {
    return ErrorSeverity.FATAL;
  }

  @Override
  public boolean shouldAudit() {
    return true; // SIEMPRE se audita
  }
}
