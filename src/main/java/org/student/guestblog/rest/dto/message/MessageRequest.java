package org.student.guestblog.rest.dto.message;

public record MessageRequest(
    String title,
    String text
) {

}
