package org.student.guestblog.rest.controller.implementation;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.student.guestblog.model.Message;
import org.student.guestblog.rest.controller.MessageController;
import org.student.guestblog.rest.dto.message.MessageRequest;
import org.student.guestblog.rest.dto.message.MessageResponse;
import org.student.guestblog.service.FileService;
import org.student.guestblog.service.MessageService;
import org.student.guestblog.service.UserService;

@Log
@RequiredArgsConstructor
@Component
public class DefaultMessageController implements MessageController {

  private final MessageService messageService;
  private final UserService userService;
  private final FileService fileService;

  @Override
  public ResponseEntity<List<MessageResponse>> getMessages() {
    var messages = messageService.getAllMessages().stream()
      .map(m -> MessageResponse.builder()
        .id(m.getId())
        .title(m.getTitle())
        .text(m.getText())
        .timestamp(m.getTimestamp())
        .file(m.getFile() != null ? m.getFile().getId() : null)
        .build())
      .collect(Collectors.toList());
    var httpStatus = !messages.isEmpty() ? HttpStatus.OK : HttpStatus.NO_CONTENT;
    return ResponseEntity.status(httpStatus).body(messages);
  }

  @Override
  public ResponseEntity<MessageResponse> getMessage(@PathVariable long id) {
    var message = messageService.getMessage(id);
    return message
      .map(m -> MessageResponse.builder()
        .id(m.getId())
        .title(m.getTitle())
        .text(m.getText())
        .timestamp(m.getTimestamp())
        .file(m.getFile() != null ? m.getFile().getId() : null)
        .build())
      .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
  }

  @Override
  public ResponseEntity<MessageResponse> addMessage(@RequestBody MessageRequest messageRequest) {
    var file = messageRequest.getFile().map(fileService::save);
    Long id = null;
    try {
      id = messageService.addMessage(
        Message.builder()
          .title(messageRequest.getTitle())
          .text(messageRequest.getText())
          .file(file.orElse(null))
          .build()
      );
    } catch (Exception e) {
      file.ifPresent(f -> fileService.delete(f.getId()));
      throw e;
    }
    return ResponseEntity.ok(MessageResponse.builder().id(id).build());
  }

  @Override
  public ResponseEntity deleteMessage(@PathVariable long id) {
    messageService.deleteMessage(id);
    return ResponseEntity.ok().build();
  }
}
