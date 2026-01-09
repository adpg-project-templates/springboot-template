package com.andrewpg.springboottemplate.entity;

import com.andrewpg.springboottemplate.config.EnversRevisionListener;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

@Entity
@Table(name = "revinfo")
@RevisionEntity(EnversRevisionListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomRevisionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @RevisionNumber
  private Long id;

  @RevisionTimestamp private Long timestamp;

  @Column private String username;

  @Column private String ipAddress;

  @Column private LocalDateTime revisionDate;

  @PrePersist
  public void prePersist() {
    if (revisionDate == null) {
      revisionDate = LocalDateTime.now();
    }
    if (timestamp == null) {
      timestamp = System.currentTimeMillis();
    }
  }
}
