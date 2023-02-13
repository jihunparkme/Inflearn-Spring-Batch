package io.springbatch.springbatchlecture.flow;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class SimpleFlowConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job SimpleFlowConfiguration() {
        return jobBuilderFactory.get("SimpleFlowConfiguration")
                .start(SimpleFlowConfigurationFlow())
                .next(SimpleFlowConfigurationStep3())
                .end()
                .build();
    }

    @Bean
    public Flow SimpleFlowConfigurationFlow() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow");

        flowBuilder.start(SimpleFlowConfigurationStep1())
                .next(SimpleFlowConfigurationStep2())
                .end();

        return flowBuilder.build();
    }

    @Bean
    public Step SimpleFlowConfigurationStep1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(">> step1 has executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step SimpleFlowConfigurationStep2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(">> step2 has executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step SimpleFlowConfigurationStep3() {
        return stepBuilderFactory.get("step3")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(">> step3 has executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
