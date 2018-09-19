package org.student.guestblog.rest.dto.user;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.student.guestblog.model.Role;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

  private String username;
  private Optional<String> email;
  private List<Role> roles;
}
