package org.student.guestblog.rest.dto.message;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record MessageRequest(
    String title,
    String text
) {

}
