package org.student.guestblog.rest.dto.message;

import org.student.guestblog.data.entity.MessageEntity;

import java.time.OffsetDateTime;
import java.util.UUID;

public record MessageResponse(
    UUID id,
    UUID authorId,
    String title,
    String text,
    OffsetDateTime createdAt,
    OffsetDateTime editedAt,
    String file
) {

    public MessageResponse(MessageEntity model) {
        this(
            model.getId(),
            model.getAuthorId(),
            model.getTitle(),
            model.getText(),
            model.getCreateDate(),
            model.getUpdateDate(),
            model.getFile() != null ? model.getFile().getFilename() : null
        );
    }
}
