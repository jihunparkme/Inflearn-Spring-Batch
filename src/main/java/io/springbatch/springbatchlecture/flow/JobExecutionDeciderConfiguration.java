package io.springbatch.springbatchlecture.flow;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class JobExecutionDeciderConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job JobExecutionDeciderConfiguration() {
        return jobBuilderFactory.get("JobExecutionDeciderConfiguration")
                .start(JobExecutionDeciderConfigurationStartStep())
                .next(JobExecutionDeciderConfigurationDecider()) // startStep() 이 성공하면 decider() 실행
                .from(JobExecutionDeciderConfigurationDecider()).on("ODD").to(oddStep()) // decider() 실행 후 상태가 ODD 면 oddStep() 실행
                .from(JobExecutionDeciderConfigurationDecider()).on("EVEN").to(evenStep()) // decider() 실행 후 상태가 EVEN 이면 evenStep() 실행
                .end()
                .build();
    }

    @Bean
    public Step JobExecutionDeciderConfigurationStartStep() {
        return stepBuilderFactory.get("startStep")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("This is the start tasklet");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step evenStep() {
        return stepBuilderFactory.get("evenStep")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(">>EvenStep has executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step oddStep() {
        return stepBuilderFactory.get("oddStep")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(">>OddStep has executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public JobExecutionDecider JobExecutionDeciderConfigurationDecider() {
        return new CustomDecider();
    }

    public static class CustomDecider implements JobExecutionDecider {

        private int count = 1;

        @Override
        public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
            count++;

            if (count % 2 == 0) {
                return new FlowExecutionStatus("EVEN");
            } else {
                return new FlowExecutionStatus("ODD");
            }
        }
    }
}
