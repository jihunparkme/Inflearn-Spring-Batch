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
public class SimpleFlowConfigurationV2 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job SimpleFlowConfigurationV2() {
        return jobBuilderFactory.get("SimpleFlowConfigurationV2")
                .start(SimpleFlowConfigurationV2Flow1())
                    .on("COMPLETED")
                    .to(SimpleFlowConfigurationV2Flow2())
                .from(SimpleFlowConfigurationV2Flow1())
                    .on("FAILED")
                    .to(SimpleFlowConfigurationV2Flow3())
                .end()
                .build();
    }
    @Bean
    public Flow SimpleFlowConfigurationV2Flow1() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow1");

        flowBuilder.start(SimpleFlowConfigurationV2Step1())
                .next(SimpleFlowConfigurationV2Step2())
                .end();

        return flowBuilder.build();
    }

    @Bean
    public Flow SimpleFlowConfigurationV2Flow2() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow2");

        flowBuilder.start(SimpleFlowConfigurationV2Flow3())
                .next(SimpleFlowConfigurationV2Step5())
                .next(SimpleFlowConfigurationV2Step6())
                .end();

        return flowBuilder.build();
    }

    @Bean
    public Flow SimpleFlowConfigurationV2Flow3() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow3");

        flowBuilder.start(SimpleFlowConfigurationV2Step3())
                .next(SimpleFlowConfigurationV2Step4())
                .end();

        return flowBuilder.build();
    }
    @Bean
    public Step SimpleFlowConfigurationV2Step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(">> step1 has executed");
//                    throw new RuntimeException("step1 was failed");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step SimpleFlowConfigurationV2Step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(">> step2 has executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step SimpleFlowConfigurationV2Step3() {
        return stepBuilderFactory.get("step3")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(">> step3 has executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step SimpleFlowConfigurationV2Step4() {
        return stepBuilderFactory.get("step4")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(">> step4 has executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step SimpleFlowConfigurationV2Step5() {
        return stepBuilderFactory.get("step5")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(">> step5 has executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step SimpleFlowConfigurationV2Step6() {
        return stepBuilderFactory.get("step6")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(">> step6 has executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}