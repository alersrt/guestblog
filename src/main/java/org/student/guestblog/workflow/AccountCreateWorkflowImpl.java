package org.student.guestblog.workflow;

import com.uber.cadence.workflow.ActivityFailureException;
import com.uber.cadence.workflow.Workflow;
import org.student.guestblog.data.entity.AccountEntity;
import org.student.guestblog.rest.dto.register.RegisterRequest;
import org.student.guestblog.rest.dto.user.UserResponse;
import org.student.guestblog.rest.dto.user.UserUpdateRequest;
import org.student.guestblog.service.AccountService;

import jakarta.validation.ConstraintViolationException;

import java.util.Optional;
import java.util.UUID;


public class AccountCreateWorkflowImpl implements AccountCreateWorkflow {

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
        if (isExist) {
            throw Workflow.wrap(new Exception("Email is not available"));
        }
        try {
            return accountActivities.create(request);
        } catch (ActivityFailureException ex) {
            return Optional.of(new UserResponse(new AccountEntity()));
        }
    }

    public AccountEntity update(UUID id, UserUpdateRequest request) {
        return accountActivities.update(id, request);
    }
}
