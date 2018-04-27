package org.student.guestblog.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * Represents information about post.
 */
@Data
@Document
public class Post {

  /** Id of the post. */
  @Id
  private String id;

  /** Title of the post. */
  private String title;

  /** Body of the post. */
  private String text;

  /** Time when this post was created/edited */
  private LocalDateTime timestamp;

  /** Flag determines was this post edited or no. */
  private boolean isEdited;
}
