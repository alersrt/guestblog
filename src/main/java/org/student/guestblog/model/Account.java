package org.student.guestblog.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Represents information about user which is stored in the database.
 */
@Entity
@Table(name = "account")
public class Account {

  /**
   * Id of the user.
   */
  @Id
  @SequenceGenerator(name = "account_id_gen", sequenceName = "account_id_seq", allocationSize = 1)
  @GeneratedValue(generator = "account_id_gen", strategy = GenerationType.IDENTITY)
  private Long id;

  @LazyCollection(value = LazyCollectionOption.FALSE)
  @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Passport> passports = new ArrayList<>();

  /**
   * E-mail of the user.
   */
  @NotBlank
  @Email
  private String email;

  private String name;

  private String avatar;

  /**
   * List of the user's messages.
   */
  @OneToMany(mappedBy = "author")
  private List<Message> messages = new ArrayList<>();

  /**
   * Authorities of this user.
   */
  @Type(type = "org.student.guestblog.model.AuthoritySetUserType")
  @Column(columnDefinition = "varchar[]")
  private Set<Authority> authorities = new HashSet<>();

  public Account() {
  }

  public Account(String email) {
    this.email = email;
    this.authorities.add(Authority.USER);
  }

  public Long id() {
    return id;
  }

  public Account setId(Long id) {
    this.id = id;
    return this;
  }

  public List<Passport> passports() {
    return passports;
  }

  private Account setPassports(List<Passport> passports) {
    this.passports = passports;
    return this;
  }

  public String email() {
    return email;
  }

  public Account setEmail(String email) {
    this.email = email;
    return this;
  }

  public String name() {
    return name;
  }

  public Account setName(String name) {
    this.name = name;
    return this;
  }

  public String avatar() {
    return avatar;
  }

  public Account setAvatar(String avatar) {
    this.avatar = avatar;
    return this;
  }

  public List<Message> messages() {
    return messages;
  }

  private Account setMessages(List<Message> messages) {
    this.messages = messages;
    return this;
  }

  public Set<Authority> authorities() {
    return authorities;
  }

  public Account setAuthorities(Set<Authority> authorities) {
    this.authorities = authorities;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Account account = (Account) o;

    return id.equals(account.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
