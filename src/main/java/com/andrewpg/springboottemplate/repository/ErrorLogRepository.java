package com.andrewpg.springboottemplate.repository;

import com.andrewpg.springboottemplate.entity.ErrorLog;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long> {

  Page<ErrorLog> findByTimestampBetween(
      LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

  Page<ErrorLog> findByExceptionType(String exceptionType, Pageable pageable);

  Page<ErrorLog> findByHttpStatus(Integer httpStatus, Pageable pageable);

  @Query("SELECT e FROM ErrorLog e WHERE e.username = :username")
  Page<ErrorLog> findByUsername(@Param("username") String username, Pageable pageable);

  @Query(
      "SELECT e FROM ErrorLog e WHERE e.timestamp >= :startDate AND e.timestamp <= :endDate AND"
          + " (:exceptionType IS NULL OR e.exceptionType = :exceptionType) AND"
          + " (:httpStatus IS NULL OR e.httpStatus = :httpStatus)")
  Page<ErrorLog> findWithFilters(
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate,
      @Param("exceptionType") String exceptionType,
      @Param("httpStatus") Integer httpStatus,
      Pageable pageable);
}
