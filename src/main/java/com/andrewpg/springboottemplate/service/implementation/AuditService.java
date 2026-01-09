package com.andrewpg.springboottemplate.service.implementation;

import com.andrewpg.springboottemplate.entity.AuditLog;
import com.andrewpg.springboottemplate.entity.ErrorLog;
import com.andrewpg.springboottemplate.exception.base.AuditableException;
import com.andrewpg.springboottemplate.exception.base.ErrorSeverity;
import com.andrewpg.springboottemplate.repository.AuditLogRepository;
import com.andrewpg.springboottemplate.repository.ErrorLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditService {

  // Repositories
  private final ErrorLogRepository errorLogRepository;
  private final AuditLogRepository auditLogRepository;

  /**
   * Registra un error en la base de datos (método original, mantiene compatibilidad)
   *
   * @param exception La excepción
   * @param httpStatus El código HTTP
   * @param message Mensaje personalizado
   * @param requestBody El cuerpo de la petición
   */
  @Transactional
  public void logError(
      Exception exception, Integer httpStatus, String message, String requestBody) {
    // Determinar severidad y si debe auditarse
    ErrorSeverity severity = ErrorSeverity.ERROR;
    String errorCode = null;

    if (exception instanceof AuditableException) {
      AuditableException auditable = (AuditableException) exception;
      severity = auditable.getSeverity();
      errorCode = auditable.getErrorCode();

      // Si NO debe auditarse, solo loguear en consola
      if (!auditable.shouldAudit()) {
        log.debug(
            "Excepción {} no auditada (severidad: {}): {}",
            exception.getClass().getSimpleName(),
            severity,
            exception.getMessage());
        return;
      }
    }

    logError(exception, httpStatus, message, requestBody, severity, errorCode);
  }

  /**
   * Registra un error con severidad y código de error personalizados
   *
   * @param exception La excepción
   * @param httpStatus El código HTTP
   * @param message Mensaje personalizado
   * @param requestBody El cuerpo de la petición
   * @param severity Nivel de severidad
   * @param errorCode Código de error personalizado
   */
  @Transactional
  public void logError(
      Exception exception,
      Integer httpStatus,
      String message,
      String requestBody,
      ErrorSeverity severity,
      String errorCode) {
    try {
      HttpServletRequest request = getCurrentRequest();
      String username = getCurrentUsername();

      String stackTrace = getStackTrace(exception);
      String requestUri = request != null ? request.getRequestURI() : null;
      String httpMethod = request != null ? request.getMethod() : null;
      String requestHeaders = request != null ? getRequestHeaders(request) : null;

      ErrorLog errorLog =
          ErrorLog.builder()
              .message(message != null ? message : exception.getMessage())
              .stackTrace(stackTrace)
              .exceptionType(exception.getClass().getName())
              .severity(severity)
              .errorCode(errorCode)
              .requestUri(requestUri)
              .httpMethod(httpMethod)
              .username(username)
              .httpStatus(httpStatus)
              .requestBody(requestBody)
              .requestHeaders(requestHeaders)
              .build();

      errorLogRepository.saveAndFlush(Objects.requireNonNull(errorLog));
      log.debug(
          "Error logged to database: {} (severity: {}, code: {})",
          errorLog.getId(),
          severity,
          errorCode);
    } catch (Exception e) {
      // Avoid infinite loop - just log to console if database logging fails
      log.error("Failed to save error log to database", e);
    }
  }

  @Transactional
  public void logAction(
      String action,
      String entityType,
      Long entityId,
      String description,
      String oldValue,
      String newValue) {
    try {
      HttpServletRequest request = getCurrentRequest();
      String username = getCurrentUsername();
      String requestUri = request != null ? request.getRequestURI() : null;
      String httpMethod = request != null ? request.getMethod() : null;
      String ipAddress = request != null ? getClientIpAddress(request) : null;

      AuditLog auditLog =
          AuditLog.builder()
              .action(action)
              .entityType(entityType)
              .entityId(entityId)
              .username(username)
              .description(description)
              .oldValue(oldValue)
              .newValue(newValue)
              .requestUri(requestUri)
              .httpMethod(httpMethod)
              .ipAddress(ipAddress)
              .build();

      auditLogRepository.saveAndFlush(Objects.requireNonNull(auditLog));
      log.debug("Audit action logged: {} - {}", action, description);
    } catch (Exception e) {
      log.error("Failed to save audit log to database", e);
    }
  }

  private String getCurrentUsername() {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication != null && authentication.isAuthenticated()) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
          return ((org.springframework.security.core.userdetails.UserDetails) principal)
              .getUsername();
        }
        return principal.toString();
      }
    } catch (Exception e) {
      log.debug("Could not get current username", e);
    }
    return null;
  }

  private HttpServletRequest getCurrentRequest() {
    try {
      ServletRequestAttributes attributes =
          (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
      return attributes != null ? attributes.getRequest() : null;
    } catch (Exception e) {
      log.debug("Could not get current request", e);
      return null;
    }
  }

  private String getStackTrace(Exception exception) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    exception.printStackTrace(pw);
    String stackTrace = sw.toString();
    // Limit stack trace to 5000 characters to avoid database issues
    return stackTrace.length() > 5000 ? stackTrace.substring(0, 5000) : stackTrace;
  }

  private String getRequestHeaders(HttpServletRequest request) {
    try {
      Map<String, String> headers = new HashMap<>();
      Enumeration<String> headerNames = request.getHeaderNames();
      while (headerNames.hasMoreElements()) {
        String headerName = headerNames.nextElement();
        headers.put(headerName, request.getHeader(headerName));
      }
      return headers.toString();
    } catch (Exception e) {
      log.debug("Could not get request headers", e);
      return null;
    }
  }

  private String getClientIpAddress(HttpServletRequest request) {
    String xForwardedFor = request.getHeader("X-Forwarded-For");
    if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
      return xForwardedFor.split(",")[0].trim();
    }
    String xRealIp = request.getHeader("X-Real-IP");
    if (xRealIp != null && !xRealIp.isEmpty()) {
      return xRealIp;
    }
    return request.getRemoteAddr();
  }
}
