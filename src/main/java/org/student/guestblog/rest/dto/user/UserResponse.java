package org.student.guestblog.rest.dto.user;

import java.util.Optional;
import java.util.Set;
import org.student.guestblog.model.Account;
import org.student.guestblog.model.Authority;

public record UserResponse(Optional<Long> id, String username, String email, Set<Authority> authorities) {

  public UserResponse(Account model) {
    this(Optional.ofNullable(model.getId()), model.getUsername(), model.getEmail(), model.getAuthoritiesSet());
  }
}
