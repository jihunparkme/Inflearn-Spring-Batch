package io.springbatch.springbatchlecture.flow.scope;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobScope_StepScope_Configuration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job JobScope_StepScope_Configuration() {
        return jobBuilderFactory.get("JobScope_StepScope_Configuration")
                .start(JobScopeStepScopeConfigurationStep1(null))
                .next(JobScopeStepScopeConfigurationStep2())
                .listener(new JobListener())
                .build();
    }

    @Bean
    @JobScope
    public Step JobScopeStepScopeConfigurationStep1(@Value("#{jobParameters['message']}") String message) {

        log.info("jobParameters['message'] : {}", message); // hello
        return stepBuilderFactory.get("step1")
                .tasklet(JobScopeStepScopeConfigurationTasklet1(null))
                .build();
    }

    @Bean
    public Step JobScopeStepScopeConfigurationStep2() {
        return stepBuilderFactory.get("step2")
                .tasklet(JobScopeStepScopeConfigurationTasklet2(null))
                .listener(new StepListener())
                .build();
    }

    @Bean
    @StepScope
    public Tasklet JobScopeStepScopeConfigurationTasklet1(@Value("#{jobExecutionContext['name']}") String name) {
        return (stepContribution, chunkContext) -> {
            log.info("jobExecutionContext['name'] : {}", name); // user1
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    @StepScope
    public Tasklet JobScopeStepScopeConfigurationTasklet2(@Value("#{stepExecutionContext['name2']}") String name2) {
        return (stepContribution, chunkContext) -> {
            log.info("jobExecutionContext['name2'] : {}", name2); // user2
            return RepeatStatus.FINISHED;
        };
    }
}