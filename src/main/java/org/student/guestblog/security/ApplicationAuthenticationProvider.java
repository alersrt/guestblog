package org.student.guestblog.security;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ApplicationAuthenticationProvider implements AuthenticationProvider {

  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public Authentication authenticate(Authentication authentication) {
    var stringToken = authentication.getCredentials().toString();

    String username;
    try {
      username = jwtTokenProvider.getUsernameFromToken(stringToken);
    } catch (Exception e) {
      username = null;
    }
    if (username != null && jwtTokenProvider.validateToken(stringToken)) {
      var claims = jwtTokenProvider.getAllClaimsFromToken(stringToken);
      List<String> roles = claims.get("roles", List.class);
      var authenticationToken = new UsernamePasswordAuthenticationToken(
        username,
        null,
        roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
      );
      return authenticationToken;
    } else {
      return null;
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return Arrays.stream(authentication.getInterfaces())
      .anyMatch(i -> i.getName().equals(Authentication.class.getName()));
  }
}
