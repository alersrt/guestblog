package org.student.guestblog.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
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

  private final MessageHandler messageHandler;
  private final FileHandler fileHandler;

  @Bean
  RouterFunction<ServerResponse> messageRouterFunction() {
    return route(GET("/api/messages").and(accept(APPLICATION_JSON)), messageHandler::getMessages)
      .andRoute(GET("/api/messages").and(accept(APPLICATION_JSON)), messageHandler::getMessage)
      .andRoute(POST("/api/messages").and(accept(APPLICATION_JSON)), messageHandler::addMessage)
      .andRoute(DELETE("/api/messages/{id}").and(accept(APPLICATION_JSON)), messageHandler::deleteMessage);
  }

  @Bean
  RouterFunction<ServerResponse> fileRouterFunction() {
    return route(GET("/api/files/{filename}"), fileHandler::getFile);
  }
}
