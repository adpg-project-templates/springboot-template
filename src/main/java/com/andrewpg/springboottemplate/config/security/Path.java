package com.andrewpg.springboottemplate.config.security;

public class Path {
  public static final String[] PUBLIC_PATHS =
      new String[] {
        "/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/actuator/**"
      };
  public static final String[] USER_PATHS = new String[] {"/user/**"};
  public static final String[] ADMIN_PATHS = new String[] {"/admin/**"};
}
