package org.student.guestblog.rest.controller;

import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/files")
public interface FileController {

  @GetMapping("/{filename}")
  ResponseEntity<GridFsResource> getFile(@PathVariable String filename);
}
