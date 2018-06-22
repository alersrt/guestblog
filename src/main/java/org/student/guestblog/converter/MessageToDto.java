package org.student.guestblog.converter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Component;
import org.student.guestblog.DTO.MessageDto;
import org.student.guestblog.entity.Message;

import com.google.common.io.ByteStreams;
import com.mongodb.client.gridfs.model.GridFSFile;

import lombok.RequiredArgsConstructor;

/** Convert {@link Message} to {@link MessageDto}. */
@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class MessageToDto implements Converter<Message, MessageDto> {

  /** Instance of the {@link GridFsOperations} for managing of the messages' attachments. */
  private final GridFsOperations gridFsOperations;

  /** {@inheritDoc} */
  @Override
  public MessageDto convert(Message message) {
    MessageDto messageDto = new MessageDto();

    messageDto.setId(message.getId());
    messageDto.setTimestamp(message.getTimestamp());
    messageDto.setTitle(message.getTitle());
    messageDto.setText(message.getText());
    messageDto.setEdited(message.isEdited());

    if (message.getFile() != null) {
      GridFSFile file = gridFsOperations
        .findOne(Query.query(Criteria.where("_id").is(message.getFile().toString())));
      GridFsResource resource = gridFsOperations.getResource(file.getFilename());
      String mime = resource.getContentType();
      byte[] bytes = new byte[0];
      try {
        bytes = ByteStreams.toByteArray(resource.getInputStream());
      } catch (IOException e) {
        e.printStackTrace();
      }
      messageDto.setFile("data:" + mime + ";base64," + Base64.getEncoder().encodeToString(bytes));
    }

    return messageDto;
  }
}
