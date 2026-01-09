package com.andrewpg.springboottemplate.exception;

import com.andrewpg.springboottemplate.exception.base.AuditableException;
import com.andrewpg.springboottemplate.exception.base.ErrorSeverity;
import lombok.Getter;

/**
 * Exception for resources not found (404). By default, it is not audited because it is common and
 * not critical.
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
    return ErrorSeverity.INFO; // Not critical
  }

  @Override
  public boolean shouldAudit() {
    return false; // Not audited by default
  }

  @Override
  public String getErrorCode() {
    return "RESOURCE_NOT_FOUND";
  }
}
