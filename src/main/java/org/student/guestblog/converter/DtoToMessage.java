package org.student.guestblog.converter;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Component;
import org.student.guestblog.dto.MessageDto;
import org.student.guestblog.entity.Message;

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

    message.setId(messageDto.getId());
    message.setTimestamp(messageDto.getTimestamp());
    message.setTitle(messageDto.getTitle());
    message.setText(messageDto.getText());
    message.setEdited(messageDto.isEdited());

    if (messageDto.getFile() != null && !messageDto.getFile().getData().isEmpty()) {
      String fileBase64 = messageDto.getFile().getData();
      String mime = fileBase64.substring(fileBase64.indexOf(":") + 1, fileBase64.indexOf(";"));

      String originalFilename = messageDto.getFile().getFilename();
      int dotIndex = originalFilename.lastIndexOf(".");
      String extension = "";
      if (dotIndex > -1) {
        extension = "." + originalFilename.substring(dotIndex + 1);
      }
      String filename = UUID.randomUUID().toString() + extension;
      ObjectId file = gridFsOperations.store(
        new ByteArrayInputStream(Base64.getDecoder().decode(messageDto.getFile().getData().split(",")[1])),
        filename,
        mime
      );
      message.setFile(file);
    }

    return message;
  }
}
