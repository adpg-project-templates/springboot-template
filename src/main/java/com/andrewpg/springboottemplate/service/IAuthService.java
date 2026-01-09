package com.andrewpg.springboottemplate.service;

import com.andrewpg.springboottemplate.dto.request.LoginRequest;
import com.andrewpg.springboottemplate.dto.request.RegisterRequest;
import com.andrewpg.springboottemplate.dto.response.JwtResponse;

public interface IAuthService {

  JwtResponse register(RegisterRequest request);

  JwtResponse login(LoginRequest request);
}
