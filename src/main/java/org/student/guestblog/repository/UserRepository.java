package org.student.guestblog.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import org.student.guestblog.model.User;
import reactor.core.publisher.Mono;

/** Represents MongoDB repository for the {@link User} entity. */
@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {

  /**
   * Returns user by its username.
   *
   * @param username name of the searched user.
   * @return user.
   */
  Mono<User> findByUsername(String username);

  /**
   * Checks if user with specified username exists.
   *
   * @param username user's username.
   * @return checking result.
   */
  Mono<Boolean> existsByUsername(String username);
}
