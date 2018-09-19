package org.student.guestblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.student.guestblog.model.Message;

/** Represents database repository for the {@link Message} entity. */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
