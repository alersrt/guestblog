package org.student.guestblog.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.student.guestblog.model.Role;
import org.student.guestblog.model.User;
import org.student.guestblog.repository.UserRepository;
import org.student.guestblog.security.JwtTokenProvider;

/**
 * Describes user's managing service and implements {@link UserDetailsService}.
 */
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final JwtTokenProvider tokenProvider;

  @Setter(onMethod = @__(@Autowired))
  private final PasswordEncoder passwordEncoder;

  @Value("${security.admin.secret}")
  private String adminSecret;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return this.getByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  /**
   * Return user by its username.
   *
   * @param username user's username.
   * @return user.
   */
  public Optional<User> getByUsername(String username) {
    if ("admin".equals(username)) {
      return Optional.of(
        User.builder()
          .username("admin")
          .password(passwordEncoder.encode(adminSecret))
          .roles(List.of(Role.ADMIN))
          .build()
      );
    } else {
      return userRepository.findByUsername(username);
    }
  }

  /**
   * Saves the new user into repository and returns saved entity.
   *
   * @param savedUser new user.
   * @return saved entity.
   */
  public Optional<User> save(User savedUser) {
    User user = User.builder()
      .username(savedUser.getUsername().toLowerCase())
      .email(savedUser.getEmail())
      .password(passwordEncoder.encode(savedUser.getPassword()))
      .roles(List.of(Role.USER))
      .build();

    var isExist = userRepository.existsByUsername(user.getUsername()) || "admin".equals(user.getUsername());
    return isExist ? Optional.empty() : Optional.of(userRepository.save(user));
  }

  /**
   * Authenticates user with specified credentials (username and password) and returns token.
   *
   * @param username username of the user.
   * @param password password of the user.
   * @return JSON web token.
   */
  public Optional<String> login(String username, String password) {
    return this.getByUsername(username)
      .map(user -> passwordEncoder.matches(password, user.getPassword())
        ? tokenProvider.generateToken(user)
        : null);
  }

  /**
   * Returns current user.
   *
   * @return current user.
   */
  public Optional<User> getCurrentUser() {
    var securityContext = SecurityContextHolder.getContext();
    var username = securityContext.getAuthentication().getName();
    return this.getByUsername(username);
  }
}
