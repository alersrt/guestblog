package org.student.guestblog.model;

import com.vladmihalcea.hibernate.type.array.EnumArrayType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Represents information about user which is stored in the database.
 */
@Entity
@Table(name = "account")
public class Account implements UserDetails {

  /**
   * Id of the user.
   */
  @Id
  @SequenceGenerator(name = "account_id_gen", sequenceName = "account_id_seq", allocationSize = 1)
  @GeneratedValue(generator = "account_id_gen")
  private Long id;

  /**
   * Username of the user.
   */
  @NotBlank
  @Size(min = 6, max = 32)
  private String username;

  /**
   * Hash code of the user password.
   */
  @NotBlank
  private String password;

  /**
   * E-mail of the user.
   */
  @Email
  private String email;

  /**
   * List of the user's messages.
   */
  @OneToMany(mappedBy = "author")
  private List<Message> messages;

  /**
   * Authorities of this user.
   */
  @Type(type = "org.student.guestblog.model.AuthoritySetUserType")
  @Column(columnDefinition = "varchar[]")
  private Set<Authority> authorities = new HashSet<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public Set<Authority> getAuthoritiesSet() {
    return authorities;
  }

  public Account setAuthorities(Set<Authority> authorities) {
    this.authorities = authorities;
    return this;
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

  public Long getId() {
    return id;
  }

  public Account setId(Long id) {
    this.id = id;
    return this;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public Account setUsername(String username) {
    this.username = username;
    return this;
  }

  @Override
  public String getPassword() {
    return password;
  }

  public Account setPassword(String password) {
    this.password = password;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public Account setEmail(String email) {
    this.email = email;
    return this;
  }

  public List<Message> getMessages() {
    return messages;
  }

  public Account setMessages(List<Message> messages) {
    this.messages = messages;
    return this;
  }
}
