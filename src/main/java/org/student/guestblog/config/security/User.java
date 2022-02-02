package org.student.guestblog.config.security;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.student.guestblog.model.Account;
import org.student.guestblog.model.Authority;
import org.student.guestblog.model.PassportType;

public record User(
    Long id,
    String email,
    String name,
    String avatar,
    Set<Authority> authorities,
    String hash,
    String clientName,
    Map<String, Object> attributes
) implements UserDetails, OAuth2User {

  public User(Account model) {
    this(
        model.id(),
        model.email(),
        model.name(),
        model.avatar(),
        model.authorities(),
        model.passports().stream()
            .filter(passport -> PassportType.PASSWORD.equals(passport.type()))
            .findFirst()
            .orElseThrow()
            .hash(),
        null,
        null
    );
  }

  public User(Account model, String clientName, Map<String, Object> attributes) {
    this(
        model.id(),
        model.email(),
        model.name(),
        model.avatar(),
        model.authorities(),
        model.passports().stream()
            .filter(passport -> clientName.equals(passport.type().id()))
            .findFirst()
            .orElseThrow()
            .hash(),
        clientName,
        attributes
    );
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return hash;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  @Override
  public String getName() {
    return name;
  }
}
