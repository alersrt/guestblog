package org.student.guestblog.rest.controller.implementation;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.student.guestblog.model.Message;
import org.student.guestblog.rest.controller.MessageController;
import org.student.guestblog.service.MessageService;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class DefaultMessageController implements MessageController {

  private final MessageService messageService;

  @Override
  public Mono<ResponseEntity> getMessages() {
    return messageService.getAllMessages()
      .collectList()
      .map(ResponseEntity::ok);
  }

  @Override
  public Mono<ResponseEntity> getMessage(@PathVariable String id) {
    return messageService.getMessage(id)
      .map(ResponseEntity::ok);
  }

  @Override
  public Mono<ResponseEntity> addMessage(@RequestBody Message message) {
    return messageService.addMessage(message)
      .map(id -> ResponseEntity.ok(Map.of("id", id)));
  }

  @Override
  public Mono<ResponseEntity> deleteMessage(@PathVariable String id) {
    return messageService.deleteMessage(id)
      .map(ResponseEntity::ok);
  }
}
