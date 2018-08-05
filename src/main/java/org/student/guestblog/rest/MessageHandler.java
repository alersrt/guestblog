package org.student.guestblog.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.student.guestblog.dto.MessageDto;
import org.student.guestblog.entity.Message;
import org.student.guestblog.service.MessageService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Component
public class MessageHandler {

  /** Instance of the {@link ConversionService} object. */
  private final ConversionService converter;

  /** Instance of the {@link MessageService}. */
  private final MessageService messageService;

  public Mono<ServerResponse> getMessages(ServerRequest request) {
    Flux<MessageDto> messageDtoFlux = messageService.getAllMessages().map(m -> converter.convert(m, MessageDto.class));
    return ok().contentType(APPLICATION_JSON).body(messageDtoFlux, MessageDto.class);
  }

  public Mono<ServerResponse> addMessage(ServerRequest request) {
    Mono<MessageDto> messageDto = request.bodyToMono(MessageDto.class);
    Mono<String> messageId = messageService.addMessage(messageDto.map(m -> converter.convert(m, Message.class)));
    return ok().contentType(APPLICATION_JSON).body(messageId, String.class);
  }

  public Mono<ServerResponse> deleteMessage(ServerRequest request) {
    Mono<String> messageId = Mono.just(request.pathVariable("id"));
    Mono<Void> result = messageService.deleteMessage(messageId);
    return ok().contentType(APPLICATION_JSON).body(result, Void.class);
  }

  public Mono<ServerResponse> getMessage(ServerRequest request) {
    Mono<String> messageId = Mono.just(request.pathVariable("id"));
    Mono<MessageDto> result = messageService.getMessage(messageId).map(m -> converter.convert(m, MessageDto.class));
    return ok().contentType(APPLICATION_JSON).body(result, MessageDto.class);
  }
}
