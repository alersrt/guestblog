package org.student.guestblog.service.impl;

import org.springframework.stereotype.Service;
import org.student.guestblog.data.entity.FileEntity;
import org.student.guestblog.data.entity.MessageEntity;
import org.student.guestblog.data.repository.MessageRepository;
import org.student.guestblog.service.MessageService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
 * Service manages of messages. Here is implemented such features as adding, deleting, editing, getting of messages.
 */
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public MessageEntity create(String title, String text, FileEntity fileEntity, UUID authorId) {
        var message = MessageEntity.builder()
                .id(UUID.randomUUID())
                .title(title)
                .text(text)
                .file(fileEntity)
                .authorId(authorId)
                .build();
        return messageRepository.save(message);
    }

    @Override
    public void delete(UUID id) {
        messageRepository.deleteById(id);
    }

    @Override
    public Optional<MessageEntity> getById(UUID id) {
        return messageRepository.findById(id);
    }

    @Override
    public List<MessageEntity> getAll() {
        return messageRepository.findAll();
    }
}
