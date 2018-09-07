package org.student.guestblog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/** Represents information about user which is stored in the database. */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class User implements UserDetails {

  /** Id of the user. */
  @JsonProperty("id")
  @Id
  private String id;

  /** Username of the user. */
  @JsonProperty("username")
  @NotBlank
  @Size(min = 6, max = 32)
  @NonNull
  private String username;

  /** Hash code of the user password. */
  @Getter(onMethod = @__(@JsonIgnore))
  @Setter(onMethod = @__(@JsonProperty("password")))
  @NotBlank
  @NonNull
  private String password;

  /** E-mail of the user. */
  @JsonProperty("email")
  @Email
  private String email;

  /** List of the user's messages. */
  @JsonIgnore
  @DBRef(db = "message", lazy = true)
  private List<Message> messages = new ArrayList<>();

  /** Authorities of this user. */
  private List<Role> roles;

  @JsonIgnore
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles.stream().map(Enum::name).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public String toString() {
    return username;
  }
}
