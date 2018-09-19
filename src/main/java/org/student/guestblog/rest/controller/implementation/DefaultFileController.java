package org.student.guestblog.rest.controller.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.student.guestblog.rest.controller.FileController;
import org.student.guestblog.service.FileService;
import org.student.guestblog.service.internal.FileResource;

@Component
@RequiredArgsConstructor
public class DefaultFileController implements FileController {

  private final FileService fileService;

  @Override
  public ResponseEntity<FileResource> getFile(@PathVariable String filename) {
    return fileService.getResource(filename)
      .map(f -> ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(f.getMime())).body(f))
      .orElseGet(() -> ResponseEntity.noContent().build());
  }
}
