package com.andrewpg.springboottemplate.exception.base;

/**
 * Interfaz para excepciones que deben ser auditadas. Las excepciones que implementen esta interfaz
 * serán registradas en la base de datos según su nivel de severidad.
 */
public interface AuditableException {

  /**
   * Obtiene el nivel de severidad de la excepción
   *
   * @return El nivel de severidad
   */
  ErrorSeverity getSeverity();

  /**
   * Indica si esta excepción debe ser auditada en la base de datos
   *
   * @return true si debe auditarse, false en caso contrario
   */
  default boolean shouldAudit() {
    // Por defecto, solo auditamos ERROR y FATAL
    return getSeverity() == ErrorSeverity.ERROR || getSeverity() == ErrorSeverity.FATAL;
  }

  /**
   * Obtiene el código de error personalizado
   *
   * @return Código de error (opcional)
   */
  default String getErrorCode() {
    return null;
  }
}
