package org.student.guestblog.service;

import java.util.Base64;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.student.guestblog.model.File;
import org.student.guestblog.repository.FileRepository;

@Log
@Service
@RequiredArgsConstructor
public class FileService {

  /**
   * Instance of the {@link FileRepository} object.
   */
  private final FileRepository fileRepository;

  /**
   * Saves base64 file's representations to database as file.
   *
   * @param base64Representation file's base 64 representation.
   * @return saved file in database.
   */
  public File save(String base64Representation) {
    return fileRepository.save(
      File.builder()
        .filename(UUID.randomUUID().toString())
        .blob(Base64.getDecoder().decode(base64Representation.split(",")[1]))
        .build()
    );
  }

  /**
   * Removes file by its id.
   *
   * @param id file's identifier.
   */
  public void delete(long id) {
    fileRepository.deleteById(id);
  }

}
