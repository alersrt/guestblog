package org.student.guestblog.rest.dto.message;

import java.time.LocalDateTime;
import org.student.guestblog.model.Message;

public record MessageResponse(
    Long id,
    Long authorId,
    String title,
    String text,
    LocalDateTime createdAt,
    LocalDateTime editedAt,
    String file
) {

  public MessageResponse(Message model) {
    this(
        model.getId(),
        model.getAuthorId(),
        model.getTitle(),
        model.getText(),
        model.getCreatedAt(),
        model.getUpdatedAt(),
        model.getFile() != null ? model.getFile().getFilename() : null
    );
  }
}
