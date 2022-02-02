package org.student.guestblog.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "passport")
public class Passport {

  @Id
  @SequenceGenerator(name = "passport_id_gen", sequenceName = "passport_id_seq", allocationSize = 1)
  @GeneratedValue(generator = "passport_id_seq")
  private Long id;

  @NotBlank
  private String hash;

  @Enumerated(EnumType.STRING)
  private PassportType type;

  @CreationTimestamp
  private LocalDateTime created;

  @UpdateTimestamp
  private LocalDateTime updated;

  @JoinColumn(name = "account_id", referencedColumnName = "id")
  @ManyToOne(fetch = FetchType.EAGER)
  private Account account;

  public Passport() {
  }

  public Passport(Account account, PassportType type, String hash) {
    this.type = type;
    this.account = account;
    this.hash = hash;
  }

  public Long id() {
    return id;
  }

  public Passport setId(Long id) {
    this.id = id;
    return this;
  }

  public String hash() {
    return hash;
  }

  public Passport setHash(String hash) {
    this.hash = hash;
    return this;
  }

  public PassportType type() {
    return type;
  }

  public Passport setType(PassportType type) {
    this.type = type;
    return this;
  }

  public LocalDateTime created() {
    return created;
  }

  public Passport setCreated(LocalDateTime created) {
    this.created = created;
    return this;
  }

  public LocalDateTime updated() {
    return updated;
  }

  public Passport setUpdated(LocalDateTime updated) {
    this.updated = updated;
    return this;
  }

  public Account account() {
    return account;
  }

  public Passport setAccount(Account account) {
    this.account = account;
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

    Passport passport = (Passport) o;

    return id.equals(passport.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
