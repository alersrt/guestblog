package org.student.guestblog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.student.guestblog.entity.Post;

/** Represents MongoDB repository for the {@link Post} entity. */
@Repository
public interface PostRepository extends MongoRepository<Post, String> {}
