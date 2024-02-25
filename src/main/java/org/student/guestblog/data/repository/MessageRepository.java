package org.student.guestblog.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.student.guestblog.data.entity.MessageEntity;

import java.util.UUID;

/**
 * Represents database repository for the {@link MessageEntity} entity.
 */
@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {

}
