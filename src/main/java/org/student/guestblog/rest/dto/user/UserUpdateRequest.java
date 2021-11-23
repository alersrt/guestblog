package org.student.guestblog.rest.dto.user;

import java.util.Optional;

public record UserUpdateRequest(Optional<String> username, Optional<String> password) {
}
