package com.andrewpg.springboottemplate.service;

import com.andrewpg.springboottemplate.entity.Role;

public interface IRoleService {

  Role findByNameOrThrow(Role.RoleName roleName);

  Role findByIdOrThrow(Long id);

  boolean existsByName(Role.RoleName roleName);
}
