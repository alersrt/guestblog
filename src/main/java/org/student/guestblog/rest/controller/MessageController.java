package org.student.guestblog.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.student.guestblog.model.Message;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/messages")
public interface MessageController {

  @GetMapping
  Mono<ResponseEntity> getMessages();

  @GetMapping("/{id}")
  Mono<ResponseEntity> getMessage(@PathVariable String id);

  @PostMapping
  Mono<ResponseEntity> addMessage(@RequestBody Message message);

  @DeleteMapping("/{id}")
  Mono<ResponseEntity> deleteMessage(@PathVariable String id);
}
