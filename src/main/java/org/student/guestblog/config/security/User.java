package org.student.guestblog.config.security;

import java.util.Collection;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.student.guestblog.model.Account;
import org.student.guestblog.model.Authority;

public record User(
    Long id,
    String username,
    Collection<GrantedAuthority> authorities,
    String password
) implements UserDetails {
  
  public User(Account model) {
    this(model.getId(), model.getUsername(), (Collection<GrantedAuthority>) model.getAuthorities(), model.getPassword());
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
}
