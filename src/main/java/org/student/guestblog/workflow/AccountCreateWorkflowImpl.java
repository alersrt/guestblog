package org.student.guestblog.workflow;

import io.temporal.activity.ActivityOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;
import org.student.guestblog.data.entity.AccountEntity;
import org.student.guestblog.rest.dto.register.RegisterRequest;
import org.student.guestblog.rest.dto.user.UserResponse;
import org.student.guestblog.rest.dto.user.UserUpdateRequest;
import org.student.guestblog.service.AccountService;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;


@WorkflowImpl(taskQueues = "DemoTaskQueue")
@Slf4j
public class AccountCreateWorkflowImpl implements AccountCreateWorkflow {

    private final AccountService accountActivities = Workflow.newActivityStub(
            AccountService.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(2))
                    .setScheduleToCloseTimeout(Duration.ofSeconds(4))
                    .build()
    );

    public Optional<UserResponse> getByEmail(String email) {
        return accountActivities.getByEmail(email);
    }

    public Optional<AccountEntity> getById(UUID accountId) {
        return accountActivities.getById(accountId);
    }

    @Override
    public Optional<UserResponse> create(RegisterRequest request) {
        var isExist = accountActivities.getByEmail(request.email()).isPresent();
        if (isExist) {
            throw Workflow.wrap(new Exception("Email is not available"));
        }
        try {
            return accountActivities.create(request);
        } catch (Exception ex) {
            log.error("Exception", ex);
            return Optional.of(new UserResponse(new AccountEntity()));
        }
    }

    public AccountEntity update(UUID id, UserUpdateRequest request) {
        return accountActivities.update(id, request);
    }
}
