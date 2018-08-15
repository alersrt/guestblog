package org.student.guestblog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
  @Size(max = 100)
  private String title;

  /** Body of the post. */
  @JsonProperty("text")
  @Size(max = 1024)
  private String text;

  /** Attachment. Name of the file in GridFS. */
  @JsonProperty("file")
  private String file;

  /** Time when this post was created or edited. */
  @JsonProperty("timestamp")
  @NotNull
  @CreatedDate
  private Date timestamp;

  /** Flag determines was this post edited or no. */
  @JsonProperty("edited")
  private boolean edited;

  /** Author of this post. */
  @JsonProperty("user")
  @DBRef(db = "user", lazy = true)
  private User user;
}
