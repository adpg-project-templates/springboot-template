package com.andrewpg.springboottemplate.exception;

import com.andrewpg.springboottemplate.dto.response.ApiResponse;
import com.andrewpg.springboottemplate.service.implementation.AuditService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

  // Services
  private final AuditService auditService;

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            (error) -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });

    ApiResponse<Map<String, String>> response =
        ApiResponse.<Map<String, String>>builder()
            .success(false)
            .message("Error de validación")
            .data(errors)
            .timestamp(java.time.LocalDateTime.now())
            .build();

    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ApiResponse<Object>> handleAuthenticationException(
      AuthenticationException ex) {
    String message = "Error de autenticación: " + ex.getMessage();
    auditService.logError(ex, HttpStatus.UNAUTHORIZED.value(), message, null);
    ApiResponse<Object> response = ApiResponse.error(message);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ApiResponse<Object>> handleBadCredentialsException(
      BadCredentialsException ex) {
    String message = "Credenciales inválidas";
    auditService.logError(ex, HttpStatus.UNAUTHORIZED.value(), message, null);
    ApiResponse<Object> response = ApiResponse.error(message);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ApiResponse<Object>> handleRuntimeException(RuntimeException ex) {
    String requestBody = getRequestBody();
    auditService.logError(ex, HttpStatus.BAD_REQUEST.value(), ex.getMessage(), requestBody);
    ApiResponse<Object> response = ApiResponse.error(ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {
    String message = "Error interno del servidor: " + ex.getMessage();
    String requestBody = getRequestBody();
    auditService.logError(ex, HttpStatus.INTERNAL_SERVER_ERROR.value(), message, requestBody);
    ApiResponse<Object> response = ApiResponse.error(message);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

  private String getRequestBody() {
    try {
      ServletRequestAttributes attributes =
          (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
      if (attributes != null) {
        // Note: Reading request body here might not work if it was already consumed
        // For production, consider using ContentCachingRequestWrapper
        return null; // Request body reading would require additional setup
      }
    } catch (Exception e) {
      log.debug("Could not read request body", e);
    }
    return null;
  }
}
