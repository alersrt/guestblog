package org.student.guestblog.workflow;

import com.uber.cadence.WorkflowIdReusePolicy;
import com.uber.cadence.workflow.WorkflowMethod;
import org.student.guestblog.config.CadenceConfig;
import org.student.guestblog.rest.dto.register.RegisterRequest;
import org.student.guestblog.rest.dto.user.UserResponse;

import java.util.Optional;


public interface AccountWorkflow {

    @WorkflowMethod(
        name = "account-workflow:create", 
        taskList = CadenceConfig.ACCOUNT_TASKS, 
        executionStartToCloseTimeoutSeconds = 10
        )
    Optional<UserResponse> create(RegisterRequest request);
}
