package com.andrewpg.springboottemplate.mapper;

import com.andrewpg.springboottemplate.dto.request.RegisterRequest;
import com.andrewpg.springboottemplate.dto.response.UserResponse;
import com.andrewpg.springboottemplate.entity.Role;
import com.andrewpg.springboottemplate.entity.User;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(target = "roles", source = "roles", qualifiedByName = "rolesToStringSet")
  UserResponse toUserResponse(User user);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "password", ignore = true)
  @Mapping(target = "enabled", constant = "true")
  @Mapping(target = "accountNonExpired", constant = "true")
  @Mapping(target = "accountNonLocked", constant = "true")
  @Mapping(target = "credentialsNonExpired", constant = "true")
  @Mapping(target = "roles", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  User toUser(RegisterRequest request);

  @Named("rolesToStringSet")
  default Set<String> rolesToStringSet(Set<Role> roles) {
    if (roles == null) {
      return Set.of();
    }
    return roles.stream().map(role -> role.getName().name()).collect(Collectors.toSet());
  }
}
