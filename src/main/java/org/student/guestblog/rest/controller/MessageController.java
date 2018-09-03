package org.student.guestblog.rest.controller;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.student.guestblog.model.Message;

@RestController
@RequestMapping("/api/messages")
public interface MessageController {

  @GetMapping
  ResponseEntity<List<Message>> getMessages();

  @GetMapping("/{id}")
  ResponseEntity<Message> getMessage(@PathVariable String id);

  @PostMapping
  ResponseEntity<Map<String, String>> addMessage(@RequestBody Message message);

  @DeleteMapping("/{id}")
  ResponseEntity deleteMessage(@PathVariable String id);
}
