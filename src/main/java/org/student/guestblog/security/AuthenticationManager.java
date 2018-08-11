package org.student.guestblog.security;

import io.jsonwebtoken.Claims;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
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
      return Mono.just(auth);
    } else {
      return Mono.empty();
    }
  }
}
