package org.student.guestblog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.student.guestblog.entity.User;

/** Represents MongoDB repository for the {@link User} entity. */
@Repository
public interface UserRepository extends MongoRepository<User, String> {}
