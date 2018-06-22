package org.student.guestblog.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Service;
import org.student.guestblog.DTO.MessageDto;
import org.student.guestblog.entity.Message;
import org.student.guestblog.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service manages of messages. Here is implemented such features as adding, deleting, editing,
 * getting of messages.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class MessageService {

  /** Instance of the {@link ConversionService} object. */
  private final ConversionService conversionService;

  /** Instance of the {@link MessageRepository} object. */
  private final MessageRepository messageRepository;

  /** Instance of the {@link GridFsOperations} for managing of the messages' attachments. */
  private final GridFsOperations gridFsOperations;

  /**
   * Add message to repository and returns id of the added message.
   *
   * @param messageDto source of the new message.
   *
   * @return String id of the new stored message.
   */
  public MessageDto addMessage(MessageDto messageDto) {
    Message message = conversionService.convert(messageDto, Message.class);
    message.setTimestamp(LocalDateTime.now());
    return conversionService.convert(messageRepository.save(message), MessageDto.class);
  }

  /**
   * Removes {@link Message} from database by it's id.
   *
   * @param messageDto received dto of message.
   */
  public void deleteMessage(MessageDto messageDto)
    throws NoSuchElementException, NoSuchFieldException {

    if (messageDto != null) {
      Optional<Message> removedMessage = messageRepository.findById(messageDto.getId());

      if (removedMessage.isPresent()) {
        if (removedMessage.get().getFile() != null) {
          gridFsOperations.delete(
            Query.query(Criteria.where("_id").is(removedMessage.get().getFile().toString())));
        }
        messageRepository.delete(removedMessage.get());
      } else {
        throw new NoSuchElementException("delete message: such element does not found");
      }
    } else {
      throw new NoSuchFieldException("delete message: field \"id\" does not defined");
    }
  }

  /**
   * Returns list of all messages in JSON format.
   *
   * @return {@link List} of the {@link MessageDto}.
   */
  public List<MessageDto> getAllMessages() {
    return messageRepository.findAll().stream()
      .map(m -> conversionService.convert(m, MessageDto.class))
      .collect(Collectors.toList());
  }
}
