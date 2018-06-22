package org.student.guestblog.DTO;

import java.time.LocalDateTime;

import org.student.guestblog.entity.Message;

import lombok.Data;
import lombok.NoArgsConstructor;

/** Data transfer object for {@link Message} */
@Data
@NoArgsConstructor
public class MessageDto {

  /** Id of the post. */
  private String id;

  /** Title of the post. */
  private String title;

  /** Body of the post. */
  private String text;

  /** Base64 represents of file. */
  private String file;

  /** Time when this post was created or edited. */
  private LocalDateTime timestamp;

  /** Flag determines was this post edited or no. */
  private boolean isEdited;
}
