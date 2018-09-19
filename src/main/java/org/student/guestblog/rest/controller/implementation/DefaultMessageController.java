package org.student.guestblog.rest.controller.implementation;

import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.student.guestblog.model.File;
import org.student.guestblog.model.Message;
import org.student.guestblog.repository.FileRepository;
import org.student.guestblog.rest.controller.MessageController;
import org.student.guestblog.service.MessageService;
import org.student.guestblog.service.UserService;

@RequiredArgsConstructor
@Component
public class DefaultMessageController implements MessageController {

  /**
   * Instance of the {@link UserService} object.
   */
  private final UserService userService;

  /**
   * Instance of the {@link MessageService} object.
   */
  private final MessageService messageService;

  /**
   * Instance of the {@link FileRepository} object.
   */
  private final FileRepository fileRepository;

  @Override
  public ResponseEntity<List<Map<String, String>>> getMessages() {
    var messages = messageService.getAllMessages().stream()
      .map(m -> Map.of(
        "id", String.valueOf(m.getId()),
        "title", m.getTitle(),
        "text", m.getText(),
        "timestamp", m.getTimestamp().toString(),
        "file", String.valueOf(m.getFile() != null
          ? m.getFile().getId()
          : null)
      ))
      .collect(Collectors.toList());
    var httpStatus = !messages.isEmpty() ? HttpStatus.OK : HttpStatus.NO_CONTENT;
    return ResponseEntity.status(httpStatus).body(messages);
  }

  @Override
  public ResponseEntity<Map<String, String>> getMessage(@PathVariable long id) {
    var message = messageService.getMessage(id);
    return message
      .map(m -> Map.of(
        "id", String.valueOf(m.getId()),
        "title", m.getTitle(),
        "text", m.getText(),
        "timestamp", m.getTimestamp().toString(),
        "file", String.valueOf(m.getFile().getId())
      ))
      .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
  }

  @Override
  public ResponseEntity<Map<String, String>> addMessage(@RequestBody Map<String, String> message) throws SQLException {

    File file = null;
    if (message.get("file") != null) {
      file = fileRepository.save(File.builder()
        .filename(UUID.randomUUID().toString())
        .blob(Base64.getDecoder().decode(message.get("file").split(",")[1]))
        .build());
    }
    var id = messageService.addMessage(Message.builder()
      .title(message.get("title"))
      .text(message.get("text"))
      .file(file)
      .build());
    return ResponseEntity.ok(Map.of("id", String.valueOf(id)));
  }

  @Override
  public ResponseEntity deleteMessage(@PathVariable long id) {
    messageService.deleteMessage(id);
    return ResponseEntity.ok().build();
  }
}
