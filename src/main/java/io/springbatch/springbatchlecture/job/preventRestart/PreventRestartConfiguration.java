package io.springbatch.springbatchlecture.job.preventRestart;

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
public class PreventRestartConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job PreventRestartConfiguration() {
        return this.jobBuilderFactory.get("PreventRestartConfiguration")
                .start(PreventRestartConfigurationStep1())
                .next(PreventRestartConfigurationStep2())
                .next(PreventRestartConfigurationStep3())
                .preventRestart() //=> 재시작을 하지 않음 (restartable = false)
                .build();
    }

    @Bean
    public Step PreventRestartConfigurationStep1() {
        return stepBuilderFactory.get("PreventRestartConfigurationStep1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step1 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step PreventRestartConfigurationStep2() {
        return stepBuilderFactory.get("PreventRestartConfigurationStep2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step2 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step PreventRestartConfigurationStep3() {
        return stepBuilderFactory.get("PreventRestartConfigurationStep3")
                .tasklet((contribution, chunkContext) -> {
                    throw new RuntimeException("step3 has failed");
//                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}