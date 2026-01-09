package com.andrewpg.springboottemplate.controller.implementation;

import com.andrewpg.springboottemplate.controller.IUserController;
import com.andrewpg.springboottemplate.dto.response.ApiResponse;
import com.andrewpg.springboottemplate.dto.response.UserResponse;
import com.andrewpg.springboottemplate.entity.User;
import com.andrewpg.springboottemplate.security.AuthUser;
import com.andrewpg.springboottemplate.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Endpoints for user management")
@SecurityRequirement(name = "bearerAuth")
public class UserController implements IUserController {

  // Services
  private final IUserService userService;

  @Override
  @GetMapping("/me")
  @Operation(
      summary = "Get current user",
      description = "Returns the authenticated user's information")
  public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser(@AuthUser User user) {
    UserResponse userResponse = userService.toUserResponse(user);
    return ResponseEntity.ok(ApiResponse.success(userResponse));
  }

  @Override
  @GetMapping("/{id}")
  @Operation(summary = "Get user by ID", description = "Returns the information of a specific user")
  public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
    UserResponse user = userService.getUserById(id);
    return ResponseEntity.ok(ApiResponse.success(user));
  }
}
