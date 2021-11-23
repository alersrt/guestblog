package org.student.guestblog.rest.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.student.guestblog.config.security.User;
import org.student.guestblog.model.Message;
import org.student.guestblog.rest.dto.message.MessageRequest;
import org.student.guestblog.rest.dto.message.MessageResponse;
import org.student.guestblog.service.FileService;
import org.student.guestblog.service.MessageService;
import org.student.guestblog.service.AccountService;

@RestController
@RequestMapping("/api/message")
public class MessageController {

  private final MessageService messageService;
  private final AccountService accountService;
  private final FileService fileService;

  public MessageController(
      MessageService messageService,
      AccountService accountService,
      FileService fileService
  ) {
    this.messageService = messageService;
    this.accountService = accountService;
    this.fileService = fileService;
  }

  @GetMapping
  public ResponseEntity<List<MessageResponse>> getMessages() {
    var messages =
        messageService.getAllMessages().stream()
            .map(MessageResponse::new)
            .collect(Collectors.toList());
    var httpStatus = !messages.isEmpty() ? HttpStatus.OK : HttpStatus.NO_CONTENT;
    return ResponseEntity.status(httpStatus).body(messages);
  }

  @GetMapping("/{id}")
  public ResponseEntity<MessageResponse> getMessage(@PathVariable Long id) {
    var message = messageService.getMessage(id);
    return message.map(MessageResponse::new)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.noContent().build());
  }

  @PostMapping
  public ResponseEntity<MessageResponse> addMessage(
      Authentication authentication,
      @RequestPart("metadata") MessageRequest metadata,
      @RequestPart("file") Optional<MultipartFile> file
  ) throws IOException {
    var storedFile = file.map(fileService::save);
    Message savedMessage = null;
    try {
      Long authorId = null;
      if (authentication != null && authentication.isAuthenticated()) {
        var user = (User) authentication.getPrincipal();
        authorId = user.id();
      }
      savedMessage = messageService.addMessage(metadata.title(), metadata.text(), storedFile.orElse(null), authorId);
    } catch (Exception e) {
      storedFile.ifPresent(fileService::delete);
      throw e;
    }
    return ResponseEntity.ok(new MessageResponse(savedMessage));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteMessage(@PathVariable long id) {
    messageService.deleteMessage(id);
    return ResponseEntity.ok().build();
  }
}
