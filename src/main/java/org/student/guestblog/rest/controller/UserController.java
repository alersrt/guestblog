package org.student.guestblog.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.student.guestblog.model.User;
import org.student.guestblog.rest.auth.AuthRequest;
import org.student.guestblog.rest.auth.AuthResponse;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
public interface UserController {

  @GetMapping("/current")
  Mono<ResponseEntity<User>> currentUser();

  @PostMapping("/register")
  Mono<ResponseEntity> register(@RequestBody User user);

  @PostMapping("/sign/in")
  Mono<ResponseEntity<AuthResponse>> signIn(@RequestBody AuthRequest authRequest);
}
