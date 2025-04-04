package org.student.guestblog.service;

import com.uber.cadence.activity.ActivityMethod;
import org.student.guestblog.config.CadenceConfig;
import org.student.guestblog.data.entity.AccountEntity;
import org.student.guestblog.rest.dto.register.RegisterRequest;
import org.student.guestblog.rest.dto.user.UserResponse;
import org.student.guestblog.rest.dto.user.UserUpdateRequest;

import java.util.Optional;
import java.util.UUID;


public interface AccountService {

    @ActivityMethod(taskList = CadenceConfig.ACCOUNT_TASKS, scheduleToStartTimeoutSeconds = 300, startToCloseTimeoutSeconds = 300)
    Optional<AccountEntity> getById(UUID accountId);

    @ActivityMethod(taskList = CadenceConfig.ACCOUNT_TASKS, scheduleToStartTimeoutSeconds = 300, startToCloseTimeoutSeconds = 300)
    Optional<UserResponse> getByEmail(String email);

    @ActivityMethod(taskList = CadenceConfig.ACCOUNT_TASKS, scheduleToStartTimeoutSeconds = 300, startToCloseTimeoutSeconds = 300)
    Optional<UserResponse> create(RegisterRequest request);

    @ActivityMethod(taskList = CadenceConfig.ACCOUNT_TASKS, scheduleToStartTimeoutSeconds = 300, startToCloseTimeoutSeconds = 300)
    AccountEntity update(UUID id, UserUpdateRequest request);
}
