package io.springbatch.springbatchlecture.execution.context;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ExecutionContextConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final ExecutionContextTasklet1 executionContextTasklet1;
    private final ExecutionContextTasklet2 executionContextTasklet2;
    private final ExecutionContextTasklet3 executionContextTasklet3;
    private final ExecutionContextTasklet4 executionContextTasklet4;

    @Bean
    public Job ExecutionContextConfiguration() {
        return this.jobBuilderFactory.get("ExecutionContextConfiguration")
                .start(ExecutionContextConfigurationStep1())
                .next(ExecutionContextConfigurationStep2())
                .next(ExecutionContextConfigurationStep3())
                .next(ExecutionContextConfigurationStep4())
                .build();
    }

    @Bean
    public Step ExecutionContextConfigurationStep1() {
        return stepBuilderFactory.get("step1")
                .tasklet(executionContextTasklet1)
                .build();
    }

    @Bean
    public Step ExecutionContextConfigurationStep2() {
        return stepBuilderFactory.get("step2")
                .tasklet(executionContextTasklet2)
                .build();
    }

    @Bean
    public Step ExecutionContextConfigurationStep3() {
        return stepBuilderFactory.get("step3")
                .tasklet(executionContextTasklet3)
                .build();
    }

    @Bean
    public Step ExecutionContextConfigurationStep4() {
        return stepBuilderFactory.get("step4")
                .tasklet(executionContextTasklet4)
                .build();
    }
}