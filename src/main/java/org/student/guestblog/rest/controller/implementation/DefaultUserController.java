package org.student.guestblog.rest.controller.implementation;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.student.guestblog.model.User;
import org.student.guestblog.rest.auth.AuthRequest;
import org.student.guestblog.rest.auth.AuthResponse;
import org.student.guestblog.rest.controller.UserController;
import org.student.guestblog.service.UserService;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DefaultUserController implements UserController {

  private final UserService userService;

  @Override
  public Mono<ResponseEntity<User>> currentUser() {
    return userService.getCurrentUser()
      .map(ResponseEntity::ok)
      .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
  }

  @Override
  public Mono<ResponseEntity> register(@RequestBody User user) {
    return userService.save(user)
      .map(u -> ResponseEntity.ok(Map.of("id", u.getId())));
  }

  @Override
  public Mono<ResponseEntity<AuthResponse>> signIn(@RequestBody AuthRequest authRequest) {
    return userService.login(authRequest.getUsername(), authRequest.getPassword())
      .map(AuthResponse::new)
      .map(ResponseEntity::ok);
  }
}
