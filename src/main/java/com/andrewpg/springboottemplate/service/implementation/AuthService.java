package com.andrewpg.springboottemplate.service.implementation;

import com.andrewpg.springboottemplate.dto.request.LoginRequest;
import com.andrewpg.springboottemplate.dto.request.RegisterRequest;
import com.andrewpg.springboottemplate.dto.response.JwtResponse;
import com.andrewpg.springboottemplate.entity.Role;
import com.andrewpg.springboottemplate.entity.User;
import com.andrewpg.springboottemplate.mapper.AuthMapper;
import com.andrewpg.springboottemplate.mapper.UserMapper;
import com.andrewpg.springboottemplate.repository.UserRepository;
import com.andrewpg.springboottemplate.security.JwtService;
import com.andrewpg.springboottemplate.security.UserDetailsServiceImpl;
import com.andrewpg.springboottemplate.service.IAuthService;
import com.andrewpg.springboottemplate.service.IRoleService;
import com.andrewpg.springboottemplate.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

  // Services
  private final IUserService userService;
  private final IRoleService roleService;
  private final JwtService jwtService;
  private final UserDetailsServiceImpl userDetailsService;

  // Mappers
  private final UserMapper userMapper;
  private final AuthMapper authMapper;

  // Repositories
  private final UserRepository userRepository;

  // Security
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  @Override
  @Transactional
  public JwtResponse register(RegisterRequest request) {
    if (userService.existsByUsername(request.getUsername())) {
      throw new RuntimeException("Error: El nombre de usuario ya está en uso");
    }

    if (userService.existsByEmail(request.getEmail())) {
      throw new RuntimeException("Error: El email ya está en uso");
    }

    // Map RegisterRequest to User using MapStruct
    User user = userMapper.toUser(request);
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    // Get role using reusable service
    Role userRole = roleService.findByNameOrThrow(Role.RoleName.ROLE_USER);
    user.getRoles().add(userRole);

    User savedUser = userRepository.save(user);

    var userDetails = userDetailsService.loadUserByUsername(savedUser.getUsername());
    String jwt = jwtService.generateToken(userDetails);

    // Map User to JwtResponse using MapStruct
    JwtResponse jwtResponse = authMapper.toJwtResponse(savedUser);
    jwtResponse.setToken(jwt);

    return jwtResponse;
  }

  @Override
  @Transactional
  public JwtResponse login(LoginRequest request) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    var userDetails = (UserDetailsServiceImpl.UserPrincipal) authentication.getPrincipal();
    String jwt = jwtService.generateToken(userDetails);

    // Use reusable service to get user
    User user = userService.findByUsernameOrThrow(userDetails.getUsername());

    // Map User to JwtResponse using MapStruct
    JwtResponse jwtResponse = authMapper.toJwtResponse(user);
    jwtResponse.setToken(jwt);

    return jwtResponse;
  }
}
