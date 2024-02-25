package org.student.guestblog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.student.guestblog.data.entity.FileEntity;
import org.student.guestblog.data.entity.MessageEntity;
import org.student.guestblog.data.repository.AccountRepository;
import org.student.guestblog.data.repository.MessageRepository;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
     * @param text  text content of the message.
     * @param fileEntity  related file.
     * @return new stored message.
     */
    public MessageEntity addMessage(String title, String text, FileEntity fileEntity, UUID authorId) {
        var message = MessageEntity.builder()
            .id(UUID.randomUUID())
            .title(title)
            .text(text)
            .file(fileEntity)
            .authorId(authorId)
            .build();
        return messageRepository.save(message);
    }

    /**
     * Removes {@link MessageEntity} from database by it's id.
     *
     * @param messageId received id of message.
     */
    public void deleteMessage(UUID messageId) {
        messageRepository.deleteById(messageId);
    }

    /**
     * Return message by its id.
     *
     * @param messageId identifier of a message.
     * @return message.
     */
    public Optional<MessageEntity> getMessage(UUID messageId) {
        return messageRepository.findById(messageId);
    }

    /**
     * Returns list of all messages in JSON format.
     *
     * @return list of the messages.
     */
    public List<MessageEntity> getAllMessages() {
        return messageRepository.findAll();
    }
}
