package org.student.guestblog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents file emtity.
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

  /**
   * Filename.
   */
  private String filename;

  /**
   * Mime type of this file.
   */
  private String mime;

  /**
   * Binary data.
   */
  private byte[] blob;
}
