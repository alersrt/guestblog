package org.student.guestblog.service;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.student.guestblog.model.File;
import org.student.guestblog.model.Message;
import org.student.guestblog.repository.MessageRepository;
import org.student.guestblog.repository.AccountRepository;

/**
 * Service manages of messages. Here is implemented such features as adding, deleting, editing, getting of messages.
 */
@Service
public class MessageService {

  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  private final MessageRepository messageRepository;
  private final AccountRepository accountRepository;

  public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
    this.messageRepository = messageRepository;
    this.accountRepository = accountRepository;
  }

  /**
   * Add message to repository and returns id of the added message.
   *
   * @param title the message title.
   * @param text text content of the message.
   * @param file related file.
   * @return new stored message.
   */
  public Message addMessage(String title, String text, File file, Long authorId) {
    var message = new Message()
        .setTitle(title)
        .setText(text)
        .setFile(file)
        .setAuthorId(authorId);
    return messageRepository.save(message);
  }

  /**
   * Removes {@link Message} from database by it's id.
   *
   * @param messageId received id of message.
   */
  public void deleteMessage(Long messageId) {
    messageRepository.deleteById(messageId);
  }

  /**
   * Return message by its id.
   *
   * @param messageId identifier of a message.
   * @return message.
   */
  public Optional<Message> getMessage(Long messageId) {
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
