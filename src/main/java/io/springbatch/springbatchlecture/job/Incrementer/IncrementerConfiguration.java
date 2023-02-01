package io.springbatch.springbatchlecture.job.Incrementer;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class IncrementerConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job IncrementerConfiguration() {
        return this.jobBuilderFactory.get("IncrementerConfiguration")
//                .incrementer(new RunIdIncrementer())
                .incrementer(new CustomJobParametersIncrementer())
                .start(IncrementerConfigurationStep1())
                .next(IncrementerConfigurationStep2())
                .next(IncrementerConfigurationStep3())
                .build();
    }

    @Bean
    public Step IncrementerConfigurationStep1() {
        return stepBuilderFactory.get("IncrementerConfigurationStep1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step1 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step IncrementerConfigurationStep2() {
        return stepBuilderFactory.get("IncrementerConfigurationStep2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step2 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step IncrementerConfigurationStep3() {
        return stepBuilderFactory.get("IncrementerConfigurationStep3")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step3 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}