package org.student.guestblog.rest.dto.register;

import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;

@Serdeable
public record RegisterResponse(UUID id) {
}
