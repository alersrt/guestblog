package org.student.guestblog.model;


import com.vladmihalcea.hibernate.type.array.ListArrayType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;

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
  @Type(ListArrayType.class)
  @Column(columnDefinition = "varchar[]")
  private List<String> authorities = new ArrayList<>();

  public Account() {
  }

  public Account(String email) {
    this.email = email;
    this.authorities.add(Authority.USER.name());
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

  public List<Authority> authorities() {
    return authorities.stream().map(Authority::valueOf).toList();
  }

  public Account setAuthorities(List<Authority> authorities) {
    this.authorities = authorities.stream().map(Authority::name).toList();
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
