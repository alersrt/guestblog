package org.student.guestblog.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.student.guestblog.service.internal.FileResource;

@RestController
@RequestMapping("/api/files")
public interface FileController {

  @CrossOrigin(origins = "*")
  @GetMapping("/{filename}")
  ResponseEntity<FileResource> getFile(@PathVariable String filename);
}
