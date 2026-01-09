package com.andrewpg.springboottemplate.service.implementation;

import com.andrewpg.springboottemplate.dto.response.UserResponse;
import com.andrewpg.springboottemplate.entity.User;
import com.andrewpg.springboottemplate.mapper.UserMapper;
import com.andrewpg.springboottemplate.repository.UserRepository;
import com.andrewpg.springboottemplate.service.IUserService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

  // Repositories
  private final UserRepository userRepository;

  // Mappers
  private final UserMapper userMapper;

  @Override
  @Transactional(readOnly = true)
  public UserResponse getUserById(Long id) {
    User user = findByIdOrThrow(id);
    return userMapper.toUserResponse(user);
  }

  @Override
  public UserResponse toUserResponse(User user) {
    return userMapper.toUserResponse(user);
  }

  @Override
  @Transactional(readOnly = true)
  public User findByIdOrThrow(Long id) {
    return userRepository
        .findById(Objects.requireNonNull(id))
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
  }

  @Override
  @Transactional(readOnly = true)
  public User findByUsernameOrThrow(String username) {
    return userRepository
        .findByUsername(username)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }
}
