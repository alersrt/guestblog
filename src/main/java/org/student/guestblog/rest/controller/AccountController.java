package org.student.guestblog.rest.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import org.student.guestblog.rest.dto.register.RegisterRequest;
import org.student.guestblog.rest.dto.register.RegisterResponse;
import org.student.guestblog.rest.dto.user.UserResponse;
import org.student.guestblog.rest.dto.user.UserUpdateRequest;
import org.student.guestblog.service.AccountService;
import org.student.guestblog.storage.entity.AccountEntity;

import java.util.UUID;

@Controller("/api/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get("/me")
    public HttpResponse<UserResponse> currentUser(Authentication authentication) {
        return accountService.getByEmail(authentication.getName())
            .map(UserResponse::new)
            .map(HttpResponse::ok)
            .orElse(HttpResponse.status(HttpStatus.FORBIDDEN));
    }

    @Post
    public HttpResponse<RegisterResponse> register(@Body RegisterRequest registerRequest) {
        return accountService.create(registerRequest.email(), registerRequest.password())
            .map(AccountEntity::getId)
            .map(id -> HttpResponse.ok(new RegisterResponse(id)))
            .orElse(HttpResponse.status(HttpStatus.CONFLICT));
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Put("/{id}")
    public HttpResponse<UserResponse> update(@PathVariable UUID id, @Body UserUpdateRequest request) {
        var updated = accountService.update(id, request.username(), request.password());
        return HttpResponse.ok(new UserResponse(updated));
    }
}
