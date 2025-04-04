package org.student.guestblog.rest.controller;

import com.uber.cadence.client.WorkflowClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.student.guestblog.rest.dto.register.RegisterRequest;
import org.student.guestblog.rest.dto.register.RegisterResponse;
import org.student.guestblog.rest.dto.user.UserResponse;
import org.student.guestblog.rest.dto.user.UserUpdateRequest;
import org.student.guestblog.security.User;
import org.student.guestblog.service.AccountService;
import org.student.guestblog.workflow.AccountCreateWorkflow;

import java.util.UUID;


@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;
    private final WorkflowClient workflowClient;

    public AccountController(AccountService accountService, WorkflowClient workflowClient) {
        this.accountService = accountService;
        this.workflowClient = workflowClient;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<UserResponse> currentUser(Authentication authentication) {
        return accountService.getByEmail(((User) authentication.getPrincipal()).email())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }

    @PostMapping
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {
        var accountWorkflow = workflowClient.newWorkflowStub(AccountCreateWorkflow.class);
        return accountWorkflow.create(registerRequest)
                .flatMap(UserResponse::id)
                .map(id -> ResponseEntity.ok(new RegisterResponse(id)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable UUID id, @RequestBody UserUpdateRequest request) {
        var updated = accountService.update(id, request);
        return ResponseEntity.ok(new UserResponse(updated));
    }
}
