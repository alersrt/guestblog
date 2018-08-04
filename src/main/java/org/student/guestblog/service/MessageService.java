package org.student.guestblog.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Service;
import org.student.guestblog.entity.Message;
import org.student.guestblog.repository.MessageRepository;
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
   * Add message to repository and returns id of the added message.
   *
   * @param message source of the new message.
   * @return identifier of the new stored message.
   */
  public Mono<String> addMessage(Mono<Message> message) {
    return message.log("message add")
      .doOnNext(m -> m.setTimestamp(LocalDateTime.now())).log("message add: set timestamp")
      .flatMap(messageRepository::save).log("message add: save to database")
      .map(Message::getId).log("message add: map to id");
  }

  /**
   * Removes {@link Message} from database by it's id.
   *
   * @param messageId received id of message.
   */
  public Mono<Void> deleteMessage(Mono<String> messageId) {
    return messageId.log("message delete")
      .flatMap(messageRepository::findById).log("message delete: find by id")
      .doOnNext(m -> gridFsOperations.delete(Query.query(Criteria.where("_id").is(m.getFile().toString()))))
      .log("message delete: remove from GridFs")
      .flatMap(m -> messageRepository.deleteById(m.getId())).log("message delete: remove from repository");
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
