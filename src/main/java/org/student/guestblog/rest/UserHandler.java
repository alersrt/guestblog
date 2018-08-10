package org.student.guestblog.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.security.Principal;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Component
public class UserHandler {

  public Mono<ServerResponse> login(ServerRequest request) {
    return request.principal()
      .map(Principal::getName)
      .flatMap(username -> ServerResponse.ok().contentType(APPLICATION_JSON)
        .syncBody(Collections.singletonMap("message", "Hello " + username + "!"))
      );
  }
}
