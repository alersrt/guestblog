package org.student.guestblog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents file.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class File {

  /** Id of the post. */
  @Id
  @GeneratedValue
  private long id;

  private String filename;

  private String mime;

  private byte[] blob;

  @OneToOne
  private Message message;
}
