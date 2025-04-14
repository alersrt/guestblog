package org.student.guestblog.service;

import io.temporal.activity.ActivityInterface;
import org.student.guestblog.data.entity.AccountEntity;
import org.student.guestblog.rest.dto.register.RegisterRequest;
import org.student.guestblog.rest.dto.user.UserResponse;
import org.student.guestblog.rest.dto.user.UserUpdateRequest;

import java.util.Optional;
import java.util.UUID;


@ActivityInterface
public interface AccountService {

    Optional<AccountEntity> getById(UUID accountId);

    Optional<UserResponse> getByEmail(String email);

    Optional<UserResponse> create(RegisterRequest request);

    AccountEntity update(UUID id, UserUpdateRequest request);
}
