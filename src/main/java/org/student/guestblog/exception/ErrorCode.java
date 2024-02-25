package org.student.guestblog.exception;

import lombok.Getter;

/**
 * Describes exceptions' codes.
 */
@Getter
public enum ErrorCode {

    GENERIC_ERROR_CODE(1);

    private final int value;

    ErrorCode(int value) {
        this.value = value;
    }
}
