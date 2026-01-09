package com.andrewpg.springboottemplate.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to automatically inject the authenticated user in controllers.
 *
 * <p>Usage:
 *
 * <pre>{@code
 * @GetMapping("/me")
 * public ResponseEntity<UserResponse> getCurrentUser(@AuthUser User user) {
 *     return ResponseEntity.ok(userService.mapToUserResponse(user));
 * }
 * }</pre>
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthUser {}
