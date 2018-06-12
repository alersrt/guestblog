package org.student.guestblog.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

/** Represents information about user which is stored in the database. */
@Data
@Builder
@Document
public class User {

  /** Id of the user. */
  @Id private String id;

  /** Username of the user. */
  private String username;

  /** Hash code of the user password. */
  private String password;

  /** E-mail of the user. */
  private String email;
}
