package org.student.guestblog.rest.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.student.guestblog.rest.dto.message.MessageRequest;
import org.student.guestblog.rest.dto.message.MessageResponse;

@RestController
@RequestMapping("/api/messages")
public interface MessageController {

  @GetMapping
  ResponseEntity<List<MessageResponse>> getMessages();

  @GetMapping("/{id}")
  ResponseEntity<MessageResponse> getMessage(@PathVariable long id);

  @PostMapping
  ResponseEntity<MessageResponse> addMessage(@RequestBody MessageRequest messageRequest);

  @DeleteMapping("/{id}")
  ResponseEntity deleteMessage(@PathVariable long id);
}
