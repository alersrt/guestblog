package org.student.guestblog.dto;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.student.guestblog.entity.Message;

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

  /** Attachment */
  private File file;

  /** Time when this post was created or edited. */
  private LocalDateTime timestamp;

  /** Flag determines was this post edited or no. */
  private boolean isEdited;
}
