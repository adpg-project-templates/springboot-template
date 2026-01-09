package com.andrewpg.springboottemplate.exception.base;

import lombok.Getter;

/**
 * Critical exception that is ALWAYS audited. Use it for system errors (database failures, external
 * services down, security issues, etc.)
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
    return true; // Always audited
  }
}
