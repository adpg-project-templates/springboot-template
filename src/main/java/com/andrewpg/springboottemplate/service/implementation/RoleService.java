package com.andrewpg.springboottemplate.service.implementation;

import com.andrewpg.springboottemplate.entity.Role;
import com.andrewpg.springboottemplate.repository.RoleRepository;
import com.andrewpg.springboottemplate.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {

  // Repositories
  private final RoleRepository roleRepository;

  @Transactional(readOnly = true)
  public Role findByNameOrThrow(Role.RoleName roleName) {
    return roleRepository
        .findByName(roleName)
        .orElseThrow(() -> new RuntimeException("Error: Rol '" + roleName + "' no encontrado"));
  }

  @Transactional(readOnly = true)
  public Role findByIdOrThrow(Long id) {
    return roleRepository
        .findById(java.util.Objects.requireNonNull(id))
        .orElseThrow(() -> new RuntimeException("Error: Rol con ID '" + id + "' no encontrado"));
  }

  @Transactional(readOnly = true)
  public boolean existsByName(Role.RoleName roleName) {
    return roleRepository.findByName(roleName).isPresent();
  }
}
