package org.student.guestblog.config;

import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.serviceclient.ClientOptions;
import com.uber.cadence.serviceclient.IWorkflowService;
import com.uber.cadence.serviceclient.WorkflowServiceTChannel;
import com.uber.cadence.worker.Worker;
import com.uber.cadence.worker.WorkerFactory;
import com.uber.cadence.worker.WorkerFactoryOptions;
import com.uber.cadence.worker.WorkerOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.student.guestblog.service.AccountService;
import org.student.guestblog.workflow.AccountCreateWorkflowImpl;


@Configuration
public class CadenceConfig {

    public static final String ACCOUNT_TASKS = "account-tasks";

    @Bean
    WorkflowClient workflowClient() {
        IWorkflowService service = new WorkflowServiceTChannel(ClientOptions.newBuilder()
                .setHost("localhost")
                .setPort(7933)
                .build());

        WorkflowClientOptions workflowClientOptions = WorkflowClientOptions.newBuilder()
                .setDomain("default")
                .build();
        return WorkflowClient.newInstance(service, workflowClientOptions);
    }

    @Autowired
    void initWorkers(AccountService accountService) {
        WorkerFactory factory = WorkerFactory.newInstance(workflowClient(),
                WorkerFactoryOptions.newBuilder()
                        .setMaxWorkflowThreadCount(1000)
                        .setStickyCacheSize(100)
                        .setDisableStickyExecution(false)
                        .build());
        Worker worker = factory.newWorker(ACCOUNT_TASKS,
                WorkerOptions.newBuilder()
                        .setMaxConcurrentActivityExecutionSize(100)
                        .setMaxConcurrentWorkflowExecutionSize(100)
                        .build());

        // Workflows are stateful. So you need a type to create instances.
        worker.registerWorkflowImplementationTypes(AccountCreateWorkflowImpl.class);
        // Activities are stateless and thread safe. So a shared instance is used.
        worker.registerActivitiesImplementations(accountService);
        // Start listening to the workflow and activity task lists.
        factory.start();
    }
}
