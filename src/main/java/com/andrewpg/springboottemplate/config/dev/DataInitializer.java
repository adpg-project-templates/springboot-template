package com.andrewpg.springboottemplate.config.dev;

import com.andrewpg.springboottemplate.entity.Role;
import com.andrewpg.springboottemplate.repository.RoleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

  // Repositories
  private final RoleRepository roleRepository;

  @Override
  public void run(String... args) {
    initializeRoles();
  }

  private void initializeRoles() {
    if (roleRepository.count() == 0) {
      log.info("Initializing roles...");

      List<Role> roles =
          List.of(
              Role.builder().name(Role.RoleName.ROLE_USER).build(),
              Role.builder().name(Role.RoleName.ROLE_ADMIN).build(),
              Role.builder().name(Role.RoleName.ROLE_MODERATOR).build());

      roleRepository.saveAll(roles);
      log.info("Roles initialized successfully");
    }
  }
}
