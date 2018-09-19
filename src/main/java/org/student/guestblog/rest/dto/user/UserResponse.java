package org.student.guestblog.rest.dto.user;

import java.util.List;
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
  private String email;
  private List<Role> roles;
}
