package org.student.guestblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationSecurityContextRepository implements SecurityContextRepository {

  private final ApplicationAuthenticationManager applicationAuthenticationManager;

  @Override
  public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
    var request = requestResponseHolder.getRequest();
    var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      var stringToken = authHeader.substring(7);
      var authenticationToken = new UsernamePasswordAuthenticationToken(stringToken, stringToken);
      var authentication = this.applicationAuthenticationManager.authenticate(authenticationToken);
      return new SecurityContextImpl(authentication);
    } else {
      return new SecurityContextImpl();
    }
  }

  @Override
  public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
    // We do not store context between requests because of REST.
  }

  @Override
  public boolean containsContext(HttpServletRequest request) {
    return true;
  }
}
