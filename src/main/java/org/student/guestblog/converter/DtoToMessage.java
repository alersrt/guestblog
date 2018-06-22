package org.student.guestblog.converter;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Component;
import org.student.guestblog.DTO.MessageDto;
import org.student.guestblog.entity.Message;

import lombok.RequiredArgsConstructor;

/** Converts {@link MessageDto} to {@link Message}. */
@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class DtoToMessage implements Converter<MessageDto, Message> {

  /** Instance of the {@link GridFsOperations} for managing of the messages' attachments. */
  private final GridFsOperations gridFsOperations;

  /** {@inheritDoc}. */
  @Override
  public Message convert(MessageDto messageDto) {
    Message message = new Message();

    message.setId(message.getId());
    message.setTimestamp(messageDto.getTimestamp());
    message.setTitle(messageDto.getTitle());
    message.setText(messageDto.getText());
    message.setEdited(messageDto.isEdited());

    if (messageDto.getFile() != null && !messageDto.getFile().isEmpty()) {
      String filename = UUID.randomUUID().toString();
      String mime = messageDto.getFile()
        .substring(messageDto.getFile().indexOf(":") + 1, messageDto.getFile().indexOf(";"));
      ObjectId file = gridFsOperations.store(
        new ByteArrayInputStream(Base64.getDecoder().decode(messageDto.getFile().split(",")[1])),
        filename,
        mime
      );
      message.setFile(file);
    }

    return message;
  }
}
