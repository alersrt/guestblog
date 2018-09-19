package org.student.guestblog.rest.controller.implementation;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.student.guestblog.model.User;
import org.student.guestblog.rest.auth.AuthRequest;
import org.student.guestblog.rest.auth.AuthResponse;
import org.student.guestblog.rest.controller.UserController;
import org.student.guestblog.service.UserService;

@Component
@RequiredArgsConstructor
public class DefaultUserController implements UserController {

  private final UserService userService;

  @Override
  public ResponseEntity<User> currentUser() {
    return userService.getCurrentUser()
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.noContent().build());
  }

  @Override
  public ResponseEntity<Map<String, Long>> register(@RequestBody User user) {
    return userService.save(user)
      .map(u -> ResponseEntity.ok(Map.of("id", u.getId())))
      .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  @Override
  public ResponseEntity<AuthResponse> signIn(@RequestBody AuthRequest authRequest) {
    return userService.login(authRequest.getUsername(), authRequest.getPassword())
      .map(token -> ResponseEntity.accepted().body(new AuthResponse(token)))
      .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
  }
}
