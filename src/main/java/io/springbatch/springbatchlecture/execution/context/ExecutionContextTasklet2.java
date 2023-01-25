package io.springbatch.springbatchlecture.execution.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExecutionContextTasklet2 implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        log.info("step2 was executed");
        ExecutionContext jobExecutionContext = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
        ExecutionContext stepExecutionContext = chunkContext.getStepContext().getStepExecution().getExecutionContext();

        log.info("jobName: " + jobExecutionContext.get("jobName")); // jobName: ExecutionContextConfiguration (Step 간 공유 가능)
        log.info("stepName: " + stepExecutionContext.get("stepName")); // stepName: null (Step 간 공유 불가능)

        String stepName = chunkContext.getStepContext().getStepExecution().getStepName();
        if (stepExecutionContext.get("stepName") == null) {
            stepExecutionContext.put("stepName", stepName);
        }
        return RepeatStatus.FINISHED;
    }
}
