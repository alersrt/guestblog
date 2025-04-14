package org.student.guestblog.workflow;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import org.student.guestblog.rest.dto.register.RegisterRequest;
import org.student.guestblog.rest.dto.user.UserResponse;

import java.util.Optional;


@WorkflowInterface
public interface AccountCreateWorkflow {

    @WorkflowMethod(name = "account-workflow:create")
    Optional<UserResponse> create(RegisterRequest request);
}
