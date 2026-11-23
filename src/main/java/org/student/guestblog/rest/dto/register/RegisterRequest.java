package org.student.guestblog.rest.dto.register;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record RegisterRequest(String email, String password) {
}
