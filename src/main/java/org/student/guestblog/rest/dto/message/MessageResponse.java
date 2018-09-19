package org.student.guestblog.rest.dto.message;

import java.util.Date;
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
  private Date timestamp;
  private Long file;
}
