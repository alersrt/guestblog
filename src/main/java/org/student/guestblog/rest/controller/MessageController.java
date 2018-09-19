package org.student.guestblog.rest.controller;

import java.sql.SQLException;
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

@RestController
@RequestMapping("/api/messages")
public interface MessageController {

  @GetMapping
  ResponseEntity<List<Map<String, String>>> getMessages();

  @GetMapping("/{id}")
  ResponseEntity<Map<String, String>> getMessage(@PathVariable long id);

  @PostMapping
  ResponseEntity<Map<String, String>> addMessage(@RequestBody Map<String, String> message) throws SQLException;

  @DeleteMapping("/{id}")
  ResponseEntity deleteMessage(@PathVariable long id);
}
