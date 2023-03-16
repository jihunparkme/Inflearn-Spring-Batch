package io.springbatch.springbatchlecture.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomStepExecutionListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        stepExecution.getExecutionContext().put("name", "user1");
        log.info("stepExecution.getStepName() : {}", stepExecution.getStepName());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("stepExecution.getExitStatus() : {}", stepExecution.getExitStatus());
        log.info("stepExecution.getStatus() : {}", stepExecution.getStatus());
        String name = (String) stepExecution.getExecutionContext().get("name");
        log.info("name : {}", name);

        return ExitStatus.COMPLETED;
    }
}
