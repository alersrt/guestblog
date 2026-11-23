package org.student.guestblog.storage.repository;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;
import org.student.guestblog.storage.entity.MessageEntity;
import org.student.guestblog.storage.jooq.tables.Message;
import org.student.guestblog.storage.jooq.tables.records.MessageRecord;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents database repository for the {@link MessageEntity} entity.
 */
@Singleton
public class MessageRepository {

    private final DSLContext dsl;
    private final FileRepository fileRepository;

    public MessageRepository(DSLContext dsl, FileRepository fileRepository) {
        this.dsl = dsl;
        this.fileRepository = fileRepository;
    }

    public Optional<MessageEntity> findById(UUID id) {
        return dsl.selectFrom(Message.MESSAGE)
            .where(Message.MESSAGE.GID.eq(id))
            .fetchOptional()
            .map(this::map);
    }

    public List<MessageEntity> findAll() {
        return dsl.selectFrom(Message.MESSAGE)
            .fetch(this::map);
    }

    public MessageEntity save(MessageEntity entity) {
        var now = OffsetDateTime.now();
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        if (entity.getCreateDate() == null) {
            entity.setCreateDate(now);
        }
        entity.setUpdateDate(now);

        var fileId = entity.getFile() != null ? entity.getFile().getId() : null;

        dsl.insertInto(Message.MESSAGE)
            .set(Message.MESSAGE.GID, entity.getId())
            .set(Message.MESSAGE.CREATE_DATE, entity.getCreateDate())
            .set(Message.MESSAGE.UPDATE_DATE, entity.getUpdateDate())
            .set(Message.MESSAGE.TITLE, entity.getTitle())
            .set(Message.MESSAGE.TEXT, entity.getText())
            .set(Message.MESSAGE.AUTHOR_GID, entity.getAuthorId())
            .set(Message.MESSAGE.FILE_GID, fileId)
            .onConflict(Message.MESSAGE.GID)
            .doUpdate()
            .set(Message.MESSAGE.UPDATE_DATE, entity.getUpdateDate())
            .set(Message.MESSAGE.TITLE, entity.getTitle())
            .set(Message.MESSAGE.TEXT, entity.getText())
            .set(Message.MESSAGE.AUTHOR_GID, entity.getAuthorId())
            .set(Message.MESSAGE.FILE_GID, fileId)
            .execute();

        return findById(entity.getId())
            .orElseThrow(() -> new IllegalStateException("Stored message is not found"));
    }

    public void deleteById(UUID id) {
        var message = findById(id);
        dsl.deleteFrom(Message.MESSAGE)
            .where(Message.MESSAGE.GID.eq(id))
            .execute();
        message.map(MessageEntity::getFile)
            .ifPresent(fileRepository::delete);
    }

    private MessageEntity map(MessageRecord record) {
        return MessageEntity.builder()
            .id(record.getGid())
            .createDate(record.getCreateDate())
            .updateDate(record.getUpdateDate())
            .title(record.getTitle())
            .text(record.getText())
            .authorId(record.getAuthorGid())
            .file(record.getFileGid() == null
                ? null
                : fileRepository.findById(record.getFileGid()).orElse(null))
            .build();
    }
}
