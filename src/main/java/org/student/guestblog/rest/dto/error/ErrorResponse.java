package org.student.guestblog.rest.dto.error;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record ErrorResponse(Integer code, String error, String message) {

    public ErrorResponse(String error, String message) {
        this(null, error, message);
    }
}
