package org.student.guestblog.service;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Service;
import org.student.guestblog.model.Message;
import org.student.guestblog.repository.MessageRepository;
import org.student.guestblog.util.MimeTypesAndExtensions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service manages of messages. Here is implemented such features as adding, deleting, editing, getting of messages.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class MessageService {

  /** Instance of the {@link GridFsOperations} for managing of the messages' attachments. */
  private final GridFsOperations gridFsOperations;

  /**
   * Instance of the {@link MessageRepository} object.
   */
  private final MessageRepository messageRepository;

  /**
   * Instance of the {@link UserService} object.
   */
  private final UserService userService;

  /**
   * Add message to repository and returns id of the added message.
   *
   * @param message source of the new message.
   * @return identifier of the new stored message.
   */
  public Mono<String> addMessage(Message message) {
    if (message.getFile() != null && !message.getFile().isEmpty()) {
      String mime = message.getFile().substring(message.getFile().indexOf(":") + 1, message.getFile().indexOf(";"));
      String filename = UUID.randomUUID().toString() + "." + MimeTypesAndExtensions.getDefaultExt(mime);
      gridFsOperations.store(
        new ByteArrayInputStream(Base64.getDecoder().decode(message.getFile().split(",")[1])),
        filename,
        mime
      );
      message.setFile(filename);
    }
    return userService.getCurrentUser()
      .doOnNext(message::setUser)
      .map(user -> message)
      .flatMap(messageRepository::save)
      .log("add message: save message")
      .map(Message::getId)
      .log("add message: get added message id");
  }

  /**
   * Removes {@link Message} from database by it's id.
   *
   * @param messageId received id of message.
   */
  public Mono<Void> deleteMessage(String messageId) {
    return messageRepository.findById(messageId)
      .log("message delete: find by id")
      .doOnNext(m -> gridFsOperations.delete(Query.query(Criteria.where("filename").is(m.getFile()))))
      .log("message delete: remove from GridFs")
      .flatMap(m -> messageRepository.deleteById(m.getId())).log("message delete: remove from repository");
  }

  /**
   * Return message by its id.
   *
   * @param messageId identifier of a message.
   * @return message.
   */
  public Mono<Message> getMessage(String messageId) {
    return messageRepository.findById(messageId);
  }

  /**
   * Returns list of all messages in JSON format.
   *
   * @return list of the messages.
   */
  public Flux<Message> getAllMessages() {
    return messageRepository.findAll();
  }
}
