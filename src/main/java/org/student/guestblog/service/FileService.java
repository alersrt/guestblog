package org.student.guestblog.service;

import java.util.Base64;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.tika.Tika;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.student.guestblog.model.File;
import org.student.guestblog.repository.FileRepository;
import org.student.guestblog.service.internal.FileResource;

@Log
@Service
@RequiredArgsConstructor
public class FileService {

  private final Tika tika;
  private final FileRepository fileRepository;

  /**
   * Returns file as {@link Resource}.
   *
   * @param filename name of the file.
   * @return Resource.
   */
  public Optional<FileResource> getResource(String filename) {
    return fileRepository.findByFilename(filename)
      .map(file -> new FileResource(file.getBlob(), file.getFilename(), file.getMime()));
  }

  /**
   * Saves base64 file's representations to database as file.
   *
   * @param base64Representation file's base 64 representation.
   * @return saved file in database.
   */
  public File save(String base64Representation) {
    var blob = Base64.getDecoder().decode(base64Representation.split(",")[1]);
    var mime = tika.detect(blob);
    String extension = null;
    try {
      extension = MimeTypes.getDefaultMimeTypes().forName(mime).getExtension();
    } catch (MimeTypeException e) {
      log.warning(e.toString());
    }
    var filename = UUID.randomUUID().toString() + extension;

    return fileRepository.save(File.builder().filename(filename).mime(mime).blob(blob).build());
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
