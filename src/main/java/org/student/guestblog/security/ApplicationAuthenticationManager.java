package org.student.guestblog.security;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
    String stringToken = authentication.getCredentials().toString();

    if (!jwtTokenProvider.validateToken(stringToken)) {
      throw new BadCredentialsException("Invalid JWT");
    }

    try {
      var username = jwtTokenProvider.getUsernameFromToken(stringToken);
      var claims = jwtTokenProvider.getAllClaimsFromToken(stringToken);
      List<String> roles = claims.get("roles", List.class);
      var authenticationToken = new UsernamePasswordAuthenticationToken(
        username,
        null,
        roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
      );
      return authenticationToken;
    } catch (Exception e) {
      return null;
    }
  }
}
