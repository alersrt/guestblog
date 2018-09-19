package org.student.guestblog.rest.controller.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.student.guestblog.model.User;
import org.student.guestblog.rest.controller.UserController;
import org.student.guestblog.rest.dto.auth.AuthRequest;
import org.student.guestblog.rest.dto.auth.AuthResponse;
import org.student.guestblog.rest.dto.register.RegisterRequest;
import org.student.guestblog.rest.dto.register.RegisterResponse;
import org.student.guestblog.rest.dto.user.UserResponse;
import org.student.guestblog.service.UserService;

@Component
@RequiredArgsConstructor
public class DefaultUserController implements UserController {

  private final UserService userService;

  @Override
  public ResponseEntity<UserResponse> currentUser() {
    return userService.getCurrentUser()
      .map(user -> UserResponse.builder()
        .username(user.getUsername())
        .email(user.getEmail())
        .roles(user.getRoles())
        .build()
      )
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.noContent().build());
  }

  @Override
  public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {
    return userService.save(
      User.builder()
        .username(registerRequest.getUsername())
        .password(registerRequest.getPassword())
        .email(registerRequest.getEmail().get())
        .build())
      .map(User::getId)
      .map(id -> ResponseEntity.ok(new RegisterResponse(id)))
      .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  @Override
  public ResponseEntity<AuthResponse> signIn(@RequestBody AuthRequest authRequest) {
    return userService.login(authRequest.getUsername(), authRequest.getPassword())
      .map(token -> ResponseEntity.accepted().body(new AuthResponse(token)))
      .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
  }
}
