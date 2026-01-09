package com.andrewpg.springboottemplate.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "audit_logs",
    indexes = {
      @Index(name = "idx_audit_logs_timestamp", columnList = "timestamp"),
      @Index(name = "idx_audit_logs_username", columnList = "username"),
      @Index(name = "idx_audit_logs_action", columnList = "action")
    })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 50)
  private String action;

  @Column(length = 100)
  private String entityType;

  @Column(length = 50)
  private Long entityId;

  @Column(length = 50)
  private String username;

  @Column(length = 500)
  private String description;

  @Column(columnDefinition = "TEXT")
  private String oldValue;

  @Column(columnDefinition = "TEXT")
  private String newValue;

  @Column(length = 500)
  private String requestUri;

  @Column(length = 10)
  private String httpMethod;

  @Column(length = 50)
  private String ipAddress;

  @Column(nullable = false)
  @Builder.Default
  private LocalDateTime timestamp = LocalDateTime.now();

  @Column(columnDefinition = "TEXT")
  private String additionalData;
}
