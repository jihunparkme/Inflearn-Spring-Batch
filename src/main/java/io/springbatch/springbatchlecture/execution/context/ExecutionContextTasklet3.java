package io.springbatch.springbatchlecture.execution.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExecutionContextTasklet3 implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        log.info("step3 was executed");
        Object name = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("name");

        if (name == null) { // 두 번째 실행에서는 step3(실패 지점) 부터 시작하고, name != null 이므로(첫 번째 실행에서 저장 후 예외 발생), 예외를 발생하지 않음.
            chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("name", "user1");
            throw new RuntimeException("step has failed");
        }

        return RepeatStatus.FINISHED;
    }
}
