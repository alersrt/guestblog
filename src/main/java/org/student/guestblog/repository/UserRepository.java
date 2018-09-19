package org.student.guestblog.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.student.guestblog.model.User;

/** Represents MongoDB repository for the {@link User} entity. */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * Returns user by its username.
   *
   * @param username name of the searched user.
   * @return user.
   */
  Optional<User> findByUsername(String username);

  /**
   * Checks if user with specified username exists.
   *
   * @param username user's username.
   * @return checking result.
   */
  boolean existsByUsername(String username);
}
