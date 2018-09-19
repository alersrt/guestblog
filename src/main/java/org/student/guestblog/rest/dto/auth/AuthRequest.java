package org.student.guestblog.rest.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class AuthRequest {

  @NonNull
  private String username;

  @NonNull
  private String password;
}
