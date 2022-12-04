package org.student.guestblog.rest.dto.user;

import org.student.guestblog.model.Account;
import org.student.guestblog.model.Authority;

import java.util.List;
import java.util.Optional;

public record UserResponse(Optional<Long> id, String email,
                           List<Authority> authorities,
                           Optional<String> avatar) {

  public UserResponse(Account model) {
    this(
      Optional.ofNullable(model.id()),
      model.email(),
      model.authorities(),
      Optional.ofNullable(model.avatar())
    );
  }
}
