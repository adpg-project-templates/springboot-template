package com.andrewpg.springboottemplate.entity;

import com.andrewpg.springboottemplate.exception.base.ErrorSeverity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "error_logs",
    indexes = {@Index(name = "idx_error_logs_timestamp", columnList = "timestamp")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 500)
  private String message;

  @Column(columnDefinition = "TEXT")
  private String stackTrace;

  @Column(length = 100)
  private String exceptionType;

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  @Builder.Default
  private ErrorSeverity severity = ErrorSeverity.ERROR;

  @Column(length = 50)
  private String errorCode;

  @Column(length = 500)
  private String requestUri;

  @Column(length = 10)
  private String httpMethod;

  @Column(length = 50)
  private String username;

  @Column(nullable = false)
  private Integer httpStatus;

  @Column(nullable = false)
  @Builder.Default
  private LocalDateTime timestamp = LocalDateTime.now();

  @Column(columnDefinition = "TEXT")
  private String requestBody;

  @Column(columnDefinition = "TEXT")
  private String requestHeaders;
}
