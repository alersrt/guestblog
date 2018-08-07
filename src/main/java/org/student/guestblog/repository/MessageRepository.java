package org.student.guestblog.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import org.student.guestblog.model.Message;

/** Represents MongoDB repository for the {@link Message} entity. */
@Repository
public interface MessageRepository extends ReactiveMongoRepository<Message, String> {}
