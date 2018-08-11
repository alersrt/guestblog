package org.student.guestblog.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.student.guestblog.model.Role;
import org.student.guestblog.model.User;
import org.student.guestblog.repository.UserRepository;
import org.student.guestblog.security.JwtTokenProvider;
import reactor.core.publisher.Mono;

/**
 * Describes user's managing service and implements {@link UserDetailsService}.
 */
@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider tokenProvider;

  @Value("${security.admin.secret}")
  private String adminSecret;

  /**
   * Return user by its username.
   *
   * @param username user's username.
   * @return user.
   */
  public Mono<User> findByUsername(String username) {
    User admin = new User();
    admin.setUsername("admin");
    admin.setPassword(passwordEncoder.encode(adminSecret));
    admin.setRoles(List.of(Role.ADMIN));

    return "admin".equals(username)
      ? Mono.just(admin)
      : userRepository.findByUsername(username);
  }

  /**
   * Saves the new user into repository and returns saved entity.
   *
   * @param user new user.
   * @return saved entity.
   */
  public Mono<User> save(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRoles(List.of(Role.USER));

    return userRepository.save(user).log("register: save");
  }

  /**
   * Authenticates user with specified credentials (username and password) and returns token.
   *
   * @param username username of the user.
   * @param password password of the user.
   * @return JSON web token.
   */
  public Mono<String> login(String username, String password) {
    return findByUsername(username)
      .log("login: find by username")
      .flatMap(user -> passwordEncoder.matches(password, user.getPassword())
        ? Mono.just(tokenProvider.generateToken(user))
        : Mono.empty())
      .log("login: return token");
  }
}
