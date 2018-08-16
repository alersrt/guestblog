package org.student.guestblog.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.student.guestblog.model.Message;
import org.student.guestblog.service.MessageService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Component
public class MessageHandler {

  /** Instance of the {@link MessageService}. */
  private final MessageService messageService;

  public Mono<ServerResponse> getMessages(ServerRequest request) {
    Flux<Message> allMessages = messageService.getAllMessages();
    return ok().contentType(APPLICATION_JSON).body(allMessages, Message.class);
  }

  public Mono<ServerResponse> addMessage(ServerRequest request) {
    return request.bodyToMono(Message.class)
      .flatMap(messageService::addMessage)
      .map(s -> Map.of("id", s))
      .flatMap(s -> ok().contentType(APPLICATION_JSON).body(Mono.just(s), Map.class));
  }

  public Mono<ServerResponse> deleteMessage(ServerRequest request) {
    return messageService.deleteMessage(request.pathVariable("id"))
      .flatMap(v -> ok().build());
  }

  public Mono<ServerResponse> getMessage(ServerRequest request) {
    return messageService.getMessage(request.pathVariable("id"))
      .flatMap(m -> ok().contentType(APPLICATION_JSON).body(Mono.just(m), Message.class))
      .switchIfEmpty(noContent().build());
  }
}
