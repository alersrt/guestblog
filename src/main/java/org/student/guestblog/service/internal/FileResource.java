package org.student.guestblog.service.internal;

import lombok.Getter;
import org.springframework.core.io.ByteArrayResource;

/**
 * Inheritor of the {@link ByteArrayResource}. Stores mime type and name of the stored file.
 */
public class FileResource extends ByteArrayResource {

  @Getter
  private String mime;

  @Getter
  private String filename;

  /**
   * Creates this resource.
   *
   * @param byteArray bytes of the file.
   * @param filename name of the file.
   * @param mime mime type of the file.
   */
  public FileResource(byte[] byteArray, String filename, String mime) {
    super(byteArray);
    this.filename = filename;
    this.mime = mime;
  }
}
