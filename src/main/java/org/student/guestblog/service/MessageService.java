package org.student.guestblog.service;

import org.student.guestblog.data.entity.FileEntity;
import org.student.guestblog.data.entity.MessageEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface MessageService {

    MessageEntity create(String title, String text, FileEntity fileEntity, UUID authorId);

    void delete(UUID id);

    Optional<MessageEntity> getById(UUID id);

    List<MessageEntity> getAll();
}
