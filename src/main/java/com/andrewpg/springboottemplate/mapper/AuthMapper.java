package com.andrewpg.springboottemplate.mapper;

import com.andrewpg.springboottemplate.dto.response.JwtResponse;
import com.andrewpg.springboottemplate.entity.Role;
import com.andrewpg.springboottemplate.entity.User;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AuthMapper {

  @Mapping(target = "token", ignore = true)
  @Mapping(target = "type", constant = "Bearer")
  @Mapping(target = "roles", source = "roles", qualifiedByName = "rolesToStringList")
  JwtResponse toJwtResponse(User user);

  @Named("rolesToStringList")
  default List<String> rolesToStringList(Set<Role> roles) {
    if (roles == null) {
      return List.of();
    }
    return roles.stream().map(role -> role.getName().name()).collect(Collectors.toList());
  }
}
