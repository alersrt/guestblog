package org.student.guestblog.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import org.student.guestblog.model.User;

/** Represents MongoDB repository for the {@link User} entity. */
@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {}
