package com.andrewpg.springboottemplate.controller;

import com.andrewpg.springboottemplate.dto.request.LoginRequest;
import com.andrewpg.springboottemplate.dto.request.RegisterRequest;
import com.andrewpg.springboottemplate.dto.response.ApiResponse;
import com.andrewpg.springboottemplate.dto.response.JwtResponse;
import org.springframework.http.ResponseEntity;

public interface IAuthController {

  ResponseEntity<ApiResponse<JwtResponse>> register(RegisterRequest request);

  ResponseEntity<ApiResponse<JwtResponse>> login(LoginRequest request);
}
