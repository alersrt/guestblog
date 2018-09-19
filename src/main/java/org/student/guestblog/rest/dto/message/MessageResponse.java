package org.student.guestblog.rest.dto.message;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {

  private Long id;
  private String title;
  private String text;
  private LocalDateTime timestamp;
  private String file;
}
