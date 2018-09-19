package org.student.guestblog.rest.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthRequest {

  private String username;
  private String password;
}
