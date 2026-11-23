package org.student.guestblog.rest.dto.user;

import io.micronaut.serde.annotation.Serdeable;

import java.util.Optional;

@Serdeable
public record UserUpdateRequest(Optional<String> username, Optional<String> password) {
}
