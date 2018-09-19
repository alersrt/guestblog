package org.student.guestblog.rest.controller;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.student.guestblog.model.User;
import org.student.guestblog.rest.auth.AuthRequest;
import org.student.guestblog.rest.auth.AuthResponse;

@RestController
@RequestMapping("/api/users")
public interface UserController {

  @GetMapping("/current")
  ResponseEntity<User> currentUser();

  @PostMapping("/register")
  ResponseEntity<Map<String, Long>> register(@RequestBody User user);

  @PostMapping("/sign/in")
  ResponseEntity<AuthResponse> signIn(@RequestBody AuthRequest authRequest);
}
