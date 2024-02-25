package org.student.guestblog.rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.student.guestblog.exception.ApplicationException;
import org.student.guestblog.rest.dto.error.ErrorResponse;

@Slf4j
@ControllerAdvice
public class ApplicationResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ApplicationException.class})
    protected ResponseEntity<Object> handleApplicationException(ApplicationException ex, WebRequest request) {
        ErrorResponse bodyOfResponse = new ErrorResponse(
            ex.getCodeValue(),
            ex.getClass().getName(),
            ex.getMessage()
        );
        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex, bodyOfResponse, HttpHeaders.EMPTY, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
