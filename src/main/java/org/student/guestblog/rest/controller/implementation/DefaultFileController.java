package org.student.guestblog.rest.controller.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.student.guestblog.rest.controller.FileController;

@Component
@RequiredArgsConstructor
public class DefaultFileController implements FileController {

  private final GridFsOperations gridFsOperations;

  @Override
  public ResponseEntity<GridFsResource> getFile(@PathVariable String filename) {
    GridFsResource resource = gridFsOperations.getResource(filename);
    return resource != null ? ResponseEntity.ok(resource) : ResponseEntity.noContent().build();
  }
}
