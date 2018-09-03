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
import org.student.guestblog.rest.register.RegisterResponse;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
public interface UserController {

  @GetMapping("/current")
  ResponseEntity<Mono<User>> currentUser();

  @PostMapping("/register")
  ResponseEntity<Mono<RegisterResponse>> register(@RequestBody User user);

  @PostMapping("/sign/in")
  ResponseEntity<Mono<AuthResponse>> signIn(@RequestBody AuthRequest authRequest);
}
