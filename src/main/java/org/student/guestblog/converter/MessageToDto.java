package org.student.guestblog.converter;

import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Component;
import org.student.guestblog.dto.File;
import org.student.guestblog.dto.MessageDto;
import org.student.guestblog.entity.Message;

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
      GridFSFile file = gridFsOperations.findOne(Query.query(Criteria.where("_id").is(message.getFile().toString())));
      messageDto.setFile(new File(file.getFilename(), message.getFile().toString()));
    }

    return messageDto;
  }
}
