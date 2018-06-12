package org.student.guestblog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.student.guestblog.entity.Message;

/** Represents MongoDB repository for the {@link Message} entity. */
@Repository
public interface MessageRepository extends MongoRepository<Message, String> {}
