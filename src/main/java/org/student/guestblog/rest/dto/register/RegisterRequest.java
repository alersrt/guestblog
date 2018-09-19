package org.student.guestblog.rest.dto.register;

import java.util.Optional;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequest {

  private String username;
  private String password;
  private Optional<String> email;
}
