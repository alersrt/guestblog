package org.student.guestblog.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.student.guestblog.rest.dto.auth.AuthRequest;
import org.student.guestblog.rest.dto.auth.AuthResponse;
import org.student.guestblog.rest.dto.register.RegisterRequest;
import org.student.guestblog.rest.dto.register.RegisterResponse;
import org.student.guestblog.rest.dto.user.UserResponse;

@RestController
@RequestMapping("/api/users")
public interface UserController {

  @GetMapping("/current")
  ResponseEntity<UserResponse> currentUser();

  @PostMapping("/register")
  ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest);

  @PostMapping("/sign/in")
  ResponseEntity<AuthResponse> signIn(@RequestBody AuthRequest authRequest);
}
