package com.andrewpg.springboottemplate.exception.base;

public enum ErrorSeverity {
  INFO, // Informative errors, not critical (e.g.: validation)
  WARNING, // Warnings, may require attention
  ERROR, // Errors that require attention
  FATAL // Critical errors that are always audited
}
