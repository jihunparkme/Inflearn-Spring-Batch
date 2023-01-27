package io.springbatch.springbatchlecture.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class JobBuilderConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

//    @Bean
//    public Job JobBuilderConfiguration1() {
//        return this.jobBuilderFactory.get("JobBuilderConfiguration")
//                .incrementer(new RunIdIncrementer())
//                .start(JobBuilderConfigurationStep1())
//                .next(JobBuilderConfigurationStep2())
//                .build();
//    }

    @Bean
    public Job JobBuilderConfiguration2() {
        return this.jobBuilderFactory.get("JobBuilderConfiguration")
                .incrementer(new RunIdIncrementer())
                .start(JobBuilderConfigurationFlow())
                .next(JobBuilderConfigurationStep2())
                .end()
                .build();
    }

    @Bean
    public Step JobBuilderConfigurationStep1() {
        return stepBuilderFactory.get("JobBuilderConfigurationStep1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(">> step1 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step JobBuilderConfigurationStep2() {
        return stepBuilderFactory.get("JobBuilderConfigurationStep2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(">> step2 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Flow JobBuilderConfigurationFlow() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("JobBuilderConfigurationFlow");
        flowBuilder.start(JobBuilderConfigurationStep3())
                .next(JobBuilderConfigurationStep4())
                .end();
        return flowBuilder.build();
    }

    @Bean
    public Step JobBuilderConfigurationStep3() {
        return stepBuilderFactory.get("JobBuilderConfigurationStep3")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println(">> step3 has executed");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step JobBuilderConfigurationStep4() {
        return stepBuilderFactory.get("JobBuilderConfigurationStep4")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(">> step4 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
