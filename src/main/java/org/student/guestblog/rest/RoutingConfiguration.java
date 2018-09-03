package org.student.guestblog.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Configuration
public class RoutingConfiguration {

  private final FileHandler fileHandler;
  private final UserHandler userHandler;

  @Bean
  RouterFunction<ServerResponse> fileRouterFunction() {
    return route(GET("/api/files/{filename}"), fileHandler::getFile);
  }

  @Bean
  RouterFunction<ServerResponse> userRouterFunction() {
    return route(POST("/api/users/register").and(accept(APPLICATION_JSON)), userHandler::register)
      .andRoute(POST("/api/users/signin").and(accept(APPLICATION_JSON)), userHandler::login)
      .andRoute(GET("/api/users/current"), userHandler::getCurrentUser);
  }
}
