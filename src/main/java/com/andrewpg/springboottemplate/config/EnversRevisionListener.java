package com.andrewpg.springboottemplate.config;

import com.andrewpg.springboottemplate.entity.CustomRevisionEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class EnversRevisionListener implements RevisionListener {

  @Override
  public void newRevision(Object revisionEntity) {
    CustomRevisionEntity revEntity = (CustomRevisionEntity) revisionEntity;

    // Get username from security context
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication != null && authentication.isAuthenticated()) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
          revEntity.setUsername(((UserDetails) principal).getUsername());
        }
      }
    } catch (Exception e) {
      // Ignore if cannot get username
    }

    // Get IP address from request
    try {
      ServletRequestAttributes attributes =
          (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
      if (attributes != null) {
        HttpServletRequest request = attributes.getRequest();
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
          revEntity.setIpAddress(xForwardedFor.split(",")[0].trim());
        } else {
          String xRealIp = request.getHeader("X-Real-IP");
          if (xRealIp != null && !xRealIp.isEmpty()) {
            revEntity.setIpAddress(xRealIp);
          } else {
            revEntity.setIpAddress(request.getRemoteAddr());
          }
        }
      }
    } catch (Exception e) {
      // Ignore if cannot get IP
    }
  }
}
