package org.student.guestblog.workflow;

import com.uber.cadence.workflow.Workflow;
import org.student.guestblog.data.entity.AccountEntity;
import org.student.guestblog.rest.dto.register.RegisterRequest;
import org.student.guestblog.rest.dto.user.UserResponse;
import org.student.guestblog.rest.dto.user.UserUpdateRequest;
import org.student.guestblog.service.AccountService;

import java.util.Optional;
import java.util.UUID;


public class AccountWorkflowImpl implements AccountWorkflow {

    private final AccountService accountActivities = Workflow.newActivityStub(AccountService.class);

    public Optional<UserResponse> getByEmail(String email) {
        return accountActivities.getByEmail(email);
    }

    public Optional<AccountEntity> getById(UUID accountId) {
        return accountActivities.getById(accountId);
    }

    @Override
    public Optional<UserResponse> create(RegisterRequest request) {
        var isExist = accountActivities.getByEmail(request.email()).isPresent();
        return isExist ? Optional.empty() : accountActivities.create(request);
    }

    public AccountEntity update(UUID id, UserUpdateRequest request) {
        return accountActivities.update(id, request);
    }
}
