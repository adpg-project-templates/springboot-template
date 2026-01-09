package com.andrewpg.springboottemplate.service;

import com.andrewpg.springboottemplate.dto.response.UserResponse;
import com.andrewpg.springboottemplate.entity.User;

public interface IUserService {

  UserResponse getUserById(Long id);

  UserResponse toUserResponse(User user);

  User findByIdOrThrow(Long id);

  User findByUsernameOrThrow(String username);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);
}
