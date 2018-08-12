package org.student.guestblog.rest;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.student.guestblog.model.User;
import org.student.guestblog.rest.auth.AuthRequest;
import org.student.guestblog.rest.auth.AuthResponse;
import org.student.guestblog.service.UserService;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Component
public class UserHandler {

  private final UserService userService;

  public Mono<ServerResponse> login(ServerRequest request) {
    return request.bodyToMono(AuthRequest.class)
      .flatMap(authRequest -> userService.login(authRequest.getUsername(), authRequest.getPassword()))
      .map(AuthResponse::new)
      .switchIfEmpty(Mono.empty())
      .flatMap(authResponse -> status(ACCEPTED).contentType(APPLICATION_JSON)
        .body(Mono.just(authResponse), AuthResponse.class))
      .switchIfEmpty(status(HttpStatus.UNAUTHORIZED).build());
  }

  public Mono<ServerResponse> register(ServerRequest request) {
    return request.bodyToMono(User.class)
      .flatMap(userService::save)
      .flatMap(user -> status(CREATED).contentType(APPLICATION_JSON)
        .body(Mono.just(Map.of("id", user.getId())), Map.class))
      .switchIfEmpty(status(HttpStatus.CONFLICT).build());
  }

  public Mono<ServerResponse> getCurrentUser(ServerRequest request) {
    return userService.getCurrentUser()
      .flatMap(user -> ok().contentType(APPLICATION_JSON).body(Mono.just(user), User.class))
      .switchIfEmpty(status(HttpStatus.NOT_FOUND).build());
  }
}
