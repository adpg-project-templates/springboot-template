package com.andrewpg.springboottemplate.controller.implementation;

import com.andrewpg.springboottemplate.controller.IAuthController;
import com.andrewpg.springboottemplate.dto.request.LoginRequest;
import com.andrewpg.springboottemplate.dto.request.RegisterRequest;
import com.andrewpg.springboottemplate.dto.response.ApiResponse;
import com.andrewpg.springboottemplate.dto.response.JwtResponse;
import com.andrewpg.springboottemplate.service.IAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user registration and login")
public class AuthController implements IAuthController {

  // Services
  private final IAuthService authService;

  @Override
  @PostMapping("/register")
  @Operation(summary = "Register new user", description = "Creates a new user account")
  public ResponseEntity<ApiResponse<JwtResponse>> register(
      @Valid @RequestBody RegisterRequest request) {
    JwtResponse response = authService.register(request);
    return ResponseEntity.ok(ApiResponse.success("Usuario registrado exitosamente", response));
  }

  @Override
  @PostMapping("/login")
  @Operation(summary = "Login", description = "Authenticates a user and returns a JWT token")
  public ResponseEntity<ApiResponse<JwtResponse>> login(@Valid @RequestBody LoginRequest request) {
    JwtResponse response = authService.login(request);
    return ResponseEntity.ok(ApiResponse.success("Inicio de sesión exitoso", response));
  }
}
