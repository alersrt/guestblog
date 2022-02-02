package org.student.guestblog.rest.dto.user;

import java.util.Optional;
import java.util.Set;
import org.student.guestblog.model.Account;
import org.student.guestblog.model.Authority;

public record UserResponse(Optional<Long> id, String email, Set<Authority> authorities, Optional<String> avatar) {

  public UserResponse(Account model) {
    this(
        Optional.ofNullable(model.id()),
        model.email(),
        model.authorities(),
        Optional.ofNullable(model.avatar())
    );
  }
}
