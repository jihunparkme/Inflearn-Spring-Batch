package io.springbatch.springbatchlecture.job.execution;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class JobExecutionConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job JobExecutionConfiguration() {
        return this.jobBuilderFactory.get("JobExecutionConfiguration")
                .start(JobExecutionConfigurationStep1())
                .next(JobExecutionConfigurationStep2())
                .build();
    }

    @Bean
    public Step JobExecutionConfigurationStep1() {
        return stepBuilderFactory.get("step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

                        JobExecution jobExecution = contribution.getStepExecution().getJobExecution();
                        System.out.println("jobExecution = " + jobExecution);

                        System.out.println("step1 has executed");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step JobExecutionConfigurationStep2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
//                    throw new RuntimeException("JobExecution has failed");
                    System.out.println("step2 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
