package org.student.guestblog.rest.dto.user;

import org.student.guestblog.data.entity.AccountEntity;
import org.student.guestblog.model.Authority;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record UserResponse(Optional<UUID> id,
                           String email,
                           List<Authority> authorities,
                           Optional<String> avatar) {

    public UserResponse(AccountEntity model) {
        this(
            Optional.ofNullable(model.getId()),
            model.getEmail(),
            model.getAuthorities().stream().map(Authority::valueOf).toList(),
            Optional.ofNullable(model.getAvatar())
        );
    }
}
