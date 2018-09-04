package org.student.guestblog.security;

import io.jsonwebtoken.Claims;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ApplicationAuthenticationManager implements AuthenticationManager {

  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public Authentication authenticate(Authentication authentication) {
    String authToken = authentication.getCredentials().toString();

    String username;
    try {
      username = jwtTokenProvider.getUsernameFromToken(authToken);
    } catch (Exception e) {
      username = null;
    }
    if (username != null && jwtTokenProvider.validateToken(authToken)) {
      Claims claims = jwtTokenProvider.getAllClaimsFromToken(authToken);
      List<String> roles = claims.get("roles", List.class);
      UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
        username,
        null,
        roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
      );
      return auth;
    } else {
      return null;
    }
  }
}
