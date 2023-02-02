package io.springbatch.springbatchlecture.step;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class StepBuilderConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job StepBuilderConfiguration() {
        return this.jobBuilderFactory.get("StepBuilderConfiguration")
                .incrementer(new RunIdIncrementer())
                .start(StepBuilderConfigurationStep1())
                .next(StepBuilderConfigurationStep2())
                .next(StepBuilderConfigurationStep4())
                .next(StepBuilderConfigurationStep5())
                .next(StepBuilderConfigurationStep3())
                .build();
    }

    @Bean
    public Step StepBuilderConfigurationStep1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step1 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step StepBuilderConfigurationStep2() {
        return stepBuilderFactory.get("step2")
                .<String, String>chunk(3)
                .reader(() -> "")
                .writer(list -> {
                })
                .build();
    }

    @Bean
    public Step StepBuilderConfigurationStep3() {
        return stepBuilderFactory.get("step3")
                .partitioner(StepBuilderConfigurationStep1())
                .gridSize(2)
                .build();
    }

    @Bean
    public Step StepBuilderConfigurationStep4() {
        return stepBuilderFactory.get("step4")
                .job(job())
                .build();
    }

    @Bean
    public Step StepBuilderConfigurationStep5() {
        return stepBuilderFactory.get("step5")
                .flow(flow())
                .build();
    }

    @Bean
    public Job job() {
        return this.jobBuilderFactory.get("job")
                .start(StepBuilderConfigurationStep1())
                .next(StepBuilderConfigurationStep2())
                .next(StepBuilderConfigurationStep3())
                .build();
    }

    @Bean
    public Flow flow() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow");
        flowBuilder.start(StepBuilderConfigurationStep2()).end();
        return flowBuilder.build();
    }
}