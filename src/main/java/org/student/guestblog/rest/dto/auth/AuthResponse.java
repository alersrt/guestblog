package org.student.guestblog.rest.dto.auth;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record AuthResponse(String token) {
}
