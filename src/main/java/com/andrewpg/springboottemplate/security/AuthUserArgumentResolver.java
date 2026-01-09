package com.andrewpg.springboottemplate.security;

import com.andrewpg.springboottemplate.entity.User;
import com.andrewpg.springboottemplate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

  // Repositories
  private final UserRepository userRepository;

  @Override
  public boolean supportsParameter(@NonNull MethodParameter parameter) {
    return parameter.hasParameterAnnotation(AuthUser.class)
        && parameter.getParameterType().equals(User.class);
  }

  @Override
  @Nullable
  public Object resolveArgument(
      @NonNull MethodParameter parameter,
      @Nullable ModelAndViewContainer mavContainer,
      @NonNull NativeWebRequest webRequest,
      @Nullable WebDataBinderFactory binderFactory) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      throw new IllegalStateException("Usuario no autenticado");
    }

    Object principal = authentication.getPrincipal();

    if (principal instanceof UserDetailsServiceImpl.UserPrincipal userPrincipal) {
      return userRepository
          .findByUsername(userPrincipal.getUsername())
          .orElseThrow(
              () -> new IllegalStateException("Usuario no encontrado en la base de datos"));
    }

    throw new IllegalStateException("Tipo de principal no soportado: " + principal.getClass());
  }
}
