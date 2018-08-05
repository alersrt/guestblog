package org.student.guestblog.entity;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/** Represents information about post. */
@Data
@NoArgsConstructor
@Document
public class Message {

  /** Id of the post. */
  @Id private String id;

  /** Title of the post. */
  private String title;

  /** Body of the post. */
  private String text;

  /** Attachment. */
  private ObjectId file;

  /** Time when this post was created or edited. */
  private LocalDateTime timestamp;

  /** Flag determines was this post edited or no. */
  private boolean isEdited;
}
