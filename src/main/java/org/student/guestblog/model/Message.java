package org.student.guestblog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/** Represents information about post. */
@Data
@NoArgsConstructor
@Document
public class Message {

  /** Id of the post. */
  @JsonProperty("id")
  @Id
  private String id;

  /** Title of the post. */
  @JsonProperty("title")
  private String title;

  /** Body of the post. */
  @JsonProperty("text")
  private String text;

  /** Attachment. Name of the file in GridFS. */
  @JsonProperty("file")
  private String file;

  /** Time when this post was created or edited. */
  @JsonProperty("timestamp")
  private LocalDateTime timestamp;

  /** Flag determines was this post edited or no. */
  @JsonProperty("isEdited")
  private boolean isEdited;
}
