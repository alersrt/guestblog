package org.student.guestblog.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/** Represents information about user which is stored in the database. */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

  /** Id of the user. */
  @Id
  @GeneratedValue
  private long id;

  /** Username of the user. */
  @NotBlank
  @Size(min = 6, max = 32)
  @NonNull
  private String username;

  /** Hash code of the user password. */
  @NotBlank
  @NonNull
  private String password;

  /** E-mail of the user. */
  @Email
  private String email;

  /** List of the user's messages. */
  @OneToMany
  private List<Message> messages;

  /** Authorities of this user. */
  @ElementCollection
  private List<Role> roles;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles.stream().map(Enum::name).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
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
  public String toString() {
    return username;
  }
}
