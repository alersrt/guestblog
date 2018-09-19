package org.student.guestblog.rest.dto.message;

import java.util.Optional;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageRequest {

  private String title;
  private String text;
  private String file;

  public Optional<String> getFile() {
    return Optional.ofNullable(file);
  }
}
