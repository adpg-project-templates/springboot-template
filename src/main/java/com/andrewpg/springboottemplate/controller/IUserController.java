package com.andrewpg.springboottemplate.controller;

import com.andrewpg.springboottemplate.dto.response.ApiResponse;
import com.andrewpg.springboottemplate.dto.response.UserResponse;
import org.springframework.http.ResponseEntity;

public interface IUserController {

  ResponseEntity<ApiResponse<UserResponse>> getCurrentUser(
      com.andrewpg.springboottemplate.entity.User user);

  ResponseEntity<ApiResponse<UserResponse>> getUserById(Long id);
}
