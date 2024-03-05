package org.student.guestblog;

import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.containers.startupcheck.IndefiniteWaitOneShotStartupCheckStrategy;
import org.testcontainers.containers.wait.strategy.AbstractWaitStrategy;

public class IndefiniteWaitOneShotWaitStrategy extends AbstractWaitStrategy {

    @Override
    protected void waitUntilReady() {
        if (!new IndefiniteWaitOneShotStartupCheckStrategy()
                .waitUntilStartupSuccessful(waitStrategyTarget.getDockerClient(), waitStrategyTarget.getContainerId())) {
            throw new IllegalStateException(waitStrategyTarget.getLogs(OutputFrame.OutputType.STDERR));
        }
    }
}
