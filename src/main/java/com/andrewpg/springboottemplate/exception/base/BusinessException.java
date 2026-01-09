package com.andrewpg.springboottemplate.exception.base;

import lombok.Getter;

/**
 * Business exception base. Use it for business logic errors that do not need to be audited
 * (validation, simple business rules, etc.)
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
    // Business exceptions are only audited if they are ERROR or FATAL
    return severity == ErrorSeverity.ERROR || severity == ErrorSeverity.FATAL;
  }
}
