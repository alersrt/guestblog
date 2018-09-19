package org.student.guestblog.rest.controller.implementation;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.student.guestblog.model.File;
import org.student.guestblog.repository.FileRepository;
import org.student.guestblog.rest.controller.FileController;

@Component
@RequiredArgsConstructor
public class DefaultFileController implements FileController {

  private final FileRepository fileRepository;

  @Override
  public ResponseEntity<Resource> getFile(@PathVariable long id) {
    Optional<File> file = fileRepository.findById(id);
    Resource resource = file
      .map(File::getBlob)
      .map(ByteArrayResource::new).get();
    return file
      .map(f -> ResponseEntity.status(HttpStatus.OK).body(resource))
      .orElseGet(() -> ResponseEntity.noContent().build());
  }
}
