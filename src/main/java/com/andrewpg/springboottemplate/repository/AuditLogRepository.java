package com.andrewpg.springboottemplate.repository;

import com.andrewpg.springboottemplate.entity.AuditLog;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

  Page<AuditLog> findByAction(String action, Pageable pageable);

  Page<AuditLog> findByUsername(String username, Pageable pageable);

  Page<AuditLog> findByEntityTypeAndEntityId(String entityType, Long entityId, Pageable pageable);

  Page<AuditLog> findByTimestampBetween(
      LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

  @Query(
      "SELECT a FROM AuditLog a WHERE a.timestamp >= :startDate AND a.timestamp <= :endDate AND"
          + " (:action IS NULL OR a.action = :action) AND"
          + " (:username IS NULL OR a.username = :username) AND"
          + " (:entityType IS NULL OR a.entityType = :entityType)")
  Page<AuditLog> findWithFilters(
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate,
      @Param("action") String action,
      @Param("username") String username,
      @Param("entityType") String entityType,
      Pageable pageable);
}
