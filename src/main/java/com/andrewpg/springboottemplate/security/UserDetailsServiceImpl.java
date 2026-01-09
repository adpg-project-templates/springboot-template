package com.andrewpg.springboottemplate.security;

import com.andrewpg.springboottemplate.entity.User;
import com.andrewpg.springboottemplate.repository.UserRepository;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  // Repositories
  private final UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

    return UserPrincipal.create(user);
  }

  public static class UserPrincipal implements UserDetails {

    private final Long id;
    private final String username;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;

    private UserPrincipal(
        Long id,
        String username,
        String email,
        String password,
        Collection<? extends GrantedAuthority> authorities,
        boolean enabled,
        boolean accountNonExpired,
        boolean accountNonLocked,
        boolean credentialsNonExpired) {
      this.id = id;
      this.username = username;
      this.email = email;
      this.password = password;
      this.authorities = authorities;
      this.enabled = enabled;
      this.accountNonExpired = accountNonExpired;
      this.accountNonLocked = accountNonLocked;
      this.credentialsNonExpired = credentialsNonExpired;
    }

    public static UserPrincipal create(User user) {
      Collection<GrantedAuthority> authorities =
          user.getRoles().stream()
              .map(role -> new SimpleGrantedAuthority(role.getName().name()))
              .collect(Collectors.toList());

      return new UserPrincipal(
          user.getId(),
          user.getUsername(),
          user.getEmail(),
          user.getPassword(),
          authorities,
          user.getEnabled(),
          user.getAccountNonExpired(),
          user.getAccountNonLocked(),
          user.getCredentialsNonExpired());
    }

    public Long getId() {
      return id;
    }

    public String getEmail() {
      return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return authorities;
    }

    @Override
    public String getPassword() {
      return password;
    }

    @Override
    public String getUsername() {
      return username;
    }

    @Override
    public boolean isAccountNonExpired() {
      return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
      return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
      return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
      return enabled;
    }
  }
}
