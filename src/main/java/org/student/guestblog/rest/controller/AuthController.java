package org.student.guestblog.rest.controller;

import com.google.common.net.HttpHeaders;
import java.lang.invoke.MethodHandles;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.student.guestblog.config.security.User;
import org.student.guestblog.rest.dto.user.UserResponse;
import org.student.guestblog.service.AccountService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  private final AccountService accountService;

  public AuthController(AccountService accountService) {
    this.accountService = accountService;
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/login")
  public ResponseEntity<UserResponse> login(Authentication authentication, HttpServletRequest request) {
    var user = (User) authentication.getPrincipal();
    log.info(String.format("[ID: %s] - [IP: %s]", user.id(), getRemoteAddress(request)));

    return accountService.getByEmail(user.getUsername())
        .map(UserResponse::new)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
  }

  private String getRemoteAddress(HttpServletRequest request) {
    String remoteAddr = request.getHeader(HttpHeaders.X_FORWARDED_FOR);
    if (remoteAddr == null) {
      remoteAddr = request.getRemoteAddr();
    } else if (remoteAddr.contains(",")) {
      remoteAddr = remoteAddr.split(",")[0];
    }

    return remoteAddr.split(":")[0].trim();
  }
}
