package com.andrewpg.springboottemplate.exception.base;

/**
 * Interface for exceptions that must be audited. Exceptions that implement this interface will be
 * registered in the database according to their severity level.
 */
public interface AuditableException {

  /**
   * Gets the severity level of the exception
   *
   * @return The severity level
   */
  ErrorSeverity getSeverity();

  /**
   * Indicates if this exception must be audited in the database
   *
   * @return true if it must be audited, false otherwise
   */
  default boolean shouldAudit() {
    // By default, we only audit ERROR and FATAL
    return getSeverity() == ErrorSeverity.ERROR || getSeverity() == ErrorSeverity.FATAL;
  }

  /**
   * Gets the custom error code
   *
   * @return Error code (optional)
   */
  default String getErrorCode() {
    return null;
  }
}
