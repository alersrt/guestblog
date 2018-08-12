package org.student.guestblog.service;

import java.security.Principal;
import java.util.List;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
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
public class UserService implements ReactiveUserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider tokenProvider;

  @Value("${security.admin.secret}")
  private String adminSecret;

  @Override
  public Mono<UserDetails> findByUsername(String username) {
    return this.getByUsername(username).map(Function.identity());
  }

  /**
   * Return user by its username.
   *
   * @param username user's username.
   * @return user.
   */
  public Mono<User> getByUsername(String username) {
    User admin = new User();
    admin.setUsername("admin");
    admin.setPassword(passwordEncoder.encode(adminSecret));
    admin.setRoles(List.of(Role.ADMIN));

    User anonymous = new User();
    anonymous.setUsername("anonymous");
    anonymous.setRoles(List.of(Role.ANONYMOUS));

    if ("admin".equals(username)) {
      return Mono.just(admin);
    } else if ("anonymous".equals(username)) {
      return Mono.just(anonymous);
    } else {
      return userRepository.findByUsername(username);
    }
  }

  /**
   * Saves the new user into repository and returns saved entity.
   *
   * @param user new user.
   * @return saved entity.
   */
  public Mono<User> save(User user) {
    user.setUsername(user.getUsername().toLowerCase());
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRoles(List.of(Role.USER));
    return userRepository.existsByUsername(user.getUsername())
      .flatMap(isExist -> isExist || "admin".equals(user.getUsername()) || "anonymous".equals(user.getUsername())
        ? Mono.empty()
        : userRepository.save(user));
  }

  /**
   * Authenticates user with specified credentials (username and password) and returns token.
   *
   * @param username username of the user.
   * @param password password of the user.
   * @return JSON web token.
   */
  public Mono<String> login(String username, String password) {
    return this.getByUsername(username)
      .log("login: find by username")
      .flatMap(user -> passwordEncoder.matches(password, user.getPassword())
        ? Mono.just(tokenProvider.generateToken(user))
        : Mono.empty())
      .log("login: return token");
  }

  /**
   * Returns current user.
   *
   * @return current user.
   */
  public Mono<User> getCurrentUser() {
    return ReactiveSecurityContextHolder.getContext()
      .map(SecurityContext::getAuthentication)
      .map(Principal::getName)
      .log("get current user: get user's name")
      .flatMap(this::getByUsername)
      .log("get current user: find by username");
  }
}
