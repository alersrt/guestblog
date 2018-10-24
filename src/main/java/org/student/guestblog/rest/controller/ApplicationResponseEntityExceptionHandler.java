package org.student.guestblog.rest.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.student.guestblog.exception.ApplicationException;
import org.student.guestblog.rest.dto.error.ErrorResponse;

@ControllerAdvice
public class ApplicationResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {ApplicationException.class})
  protected ResponseEntity<Object> handleApplicationException(ApplicationException ex, WebRequest request) {
    ErrorResponse bodyOfResponse = ErrorResponse.builder()
      .code(String.valueOf(ex.getCodeValue()))
      .error(ex.getClass().getName())
      .message(ex.getMessage())
      .build();
    return handleExceptionInternal(ex, bodyOfResponse, HttpHeaders.EMPTY, HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  @ExceptionHandler(value = {Exception.class})
  protected ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
    ErrorResponse bodyOfResponse = ErrorResponse.builder()
      .error(ex.getClass().getName())
      .message(ex.getMessage())
      .build();
    return handleExceptionInternal(ex, bodyOfResponse, HttpHeaders.EMPTY, HttpStatus.INTERNAL_SERVER_ERROR, request);
  }
}
