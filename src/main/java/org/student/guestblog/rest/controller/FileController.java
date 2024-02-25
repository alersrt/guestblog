package org.student.guestblog.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.student.guestblog.service.FileService;
import org.student.guestblog.model.internal.FileResource;

@RestController
@RequestMapping("/api/file")
public class FileController {

  private final FileService fileService;

  public FileController(FileService fileService) {
    this.fileService = fileService;
  }

  @GetMapping("/{filename}")
  public ResponseEntity<FileResource> getFile(@PathVariable String filename) {
    return fileService.getResource(filename)
      .map(f -> ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(f.getMime())).body(f))
      .orElseGet(() -> ResponseEntity.noContent().build());
  }
}
