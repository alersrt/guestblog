package org.student.guestblog.rest.controller.implementation;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.student.guestblog.model.Message;
import org.student.guestblog.rest.controller.MessageController;
import org.student.guestblog.service.MessageService;

@RequiredArgsConstructor
@Component
public class DefaultMessageController implements MessageController {

  private final MessageService messageService;

  @Override
  public ResponseEntity<List<Message>> getMessages() {
    var messages = messageService.getAllMessages();
    var httpStatus = !messages.isEmpty() ? HttpStatus.OK : HttpStatus.NO_CONTENT;
    return ResponseEntity.status(httpStatus).body(messages);
  }

  @Override
  public ResponseEntity<Message> getMessage(@PathVariable String id) {
    var message = messageService.getMessage(id);
    return message.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
  }

  @Override
  public ResponseEntity<Map<String, String>> addMessage(@RequestBody Message message) {
    var id = messageService.addMessage(message);
    return ResponseEntity.ok(Map.of("id", id));
  }

  @Override
  public ResponseEntity deleteMessage(@PathVariable String id) {
    messageService.deleteMessage(id);
    return ResponseEntity.ok().build();
  }
}
