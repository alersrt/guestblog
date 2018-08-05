package org.student.guestblog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class File {

  /** Name of the file. */
  private String filename;

  /** Base64 represents of file or its identifier. */
  private String data;
}
