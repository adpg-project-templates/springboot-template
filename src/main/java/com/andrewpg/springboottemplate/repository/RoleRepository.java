package com.andrewpg.springboottemplate.repository;

import com.andrewpg.springboottemplate.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(Role.RoleName name);
}
