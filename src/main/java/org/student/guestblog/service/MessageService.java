package org.student.guestblog.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.student.guestblog.model.Message;
import org.student.guestblog.repository.MessageRepository;

/**
 * Service manages of messages. Here is implemented such features as adding, deleting, editing, getting of messages.
 */
@Log
@Service
@RequiredArgsConstructor
public class MessageService {

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
  public long addMessage(Message message) {
    return messageRepository.save(message).getId();
  }

  /**
   * Removes {@link Message} from database by it's id.
   *
   * @param messageId received id of message.
   */
  public void deleteMessage(long messageId) {
    messageRepository.deleteById(messageId);
  }

  /**
   * Return message by its id.
   *
   * @param messageId identifier of a message.
   * @return message.
   */
  public Optional<Message> getMessage(long messageId) {
    return messageRepository.findById(messageId);
  }

  /**
   * Returns list of all messages in JSON format.
   *
   * @return list of the messages.
   */
  public List<Message> getAllMessages() {
    return messageRepository.findAll();
  }
}
