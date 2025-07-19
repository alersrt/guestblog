package org.student.guestblog.rest.controller;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.student.guestblog.exception.ApplicationException;
import org.student.guestblog.rest.dto.error.ErrorResponse;

@Slf4j
@Produces
@Singleton
@Requires(classes = {ExceptionHandler.class, ApplicationException.class})
public class ApplicationResponseEntityExceptionHandler implements ExceptionHandler<ApplicationException, HttpResponse<ErrorResponse>> {

    @Override
    public HttpResponse<ErrorResponse> handle(HttpRequest request, ApplicationException exception) {
        log.error(exception.getMessage(), exception);
        ErrorResponse bodyOfResponse = new ErrorResponse(
            exception.getCodeValue(),
            exception.getClass().getName(),
            exception.getMessage()
        );
        return HttpResponse.serverError(bodyOfResponse);
    }
}
