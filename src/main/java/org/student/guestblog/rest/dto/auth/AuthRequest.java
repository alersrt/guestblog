package org.student.guestblog.rest.dto.auth;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record AuthRequest(String username, String password) {}
