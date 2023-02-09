package io.springbatch.springbatchlecture.flow;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class StartNextConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job StartNextConfiguration() {
        return jobBuilderFactory.get("StartNextConfiguration")
                .start(StartNextConfigurationFlowA())
                .next(StartNextConfigurationStep3())
                .next(StartNextConfigurationFlowB())
                .next(StartNextConfigurationStep6())
                .end()
                .build();
    }

    @Bean
    public Flow StartNextConfigurationFlowA() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flowA");
        flowBuilder.start(StartNextConfigurationStep1())
                .next(StartNextConfigurationStep2())
                .end();

        return flowBuilder.build();
    }

    @Bean
    public Flow StartNextConfigurationFlowB() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flowB");
        flowBuilder.start(StartNextConfigurationStep4())
                .next(StartNextConfigurationStep5())
                .end();

        return flowBuilder.build();
    }

    @Bean
    public Step StartNextConfigurationStep1() {
        return stepBuilderFactory.get("step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println(">> step1 has executed");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step StartNextConfigurationStep2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(">> step2 has executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step StartNextConfigurationStep3() {
        return stepBuilderFactory.get("step3")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println(">> step3 has executed");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step StartNextConfigurationStep4() {
        return stepBuilderFactory.get("step4")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println(">> step4 has executed");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step StartNextConfigurationStep5() {
        return stepBuilderFactory.get("step5")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println(">> step5 has executed");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step StartNextConfigurationStep6() {
        return stepBuilderFactory.get("step6")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println(">> step6 has executed");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

}
