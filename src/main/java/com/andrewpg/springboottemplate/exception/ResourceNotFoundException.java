package com.andrewpg.springboottemplate.exception;

import com.andrewpg.springboottemplate.exception.base.AuditableException;
import com.andrewpg.springboottemplate.exception.base.ErrorSeverity;
import lombok.Getter;

/**
 * Excepción para recursos no encontrados (404). Por defecto NO se audita porque es común y no es
 * crítico.
 */
@Getter
public class ResourceNotFoundException extends RuntimeException implements AuditableException {

  private final String resourceName;
  private final Object resourceId;

  public ResourceNotFoundException(String resourceName, Object resourceId) {
    super(String.format("%s no encontrado con id: %s", resourceName, resourceId));
    this.resourceName = resourceName;
    this.resourceId = resourceId;
  }

  public ResourceNotFoundException(String message) {
    super(message);
    this.resourceName = null;
    this.resourceId = null;
  }

  @Override
  public ErrorSeverity getSeverity() {
    return ErrorSeverity.INFO; // No es crítico
  }

  @Override
  public boolean shouldAudit() {
    return false; // NO se audita por defecto
  }

  @Override
  public String getErrorCode() {
    return "RESOURCE_NOT_FOUND";
  }
}
