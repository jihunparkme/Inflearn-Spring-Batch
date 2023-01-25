package io.springbatch.springbatchlecture.step;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class StepConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job StepConfiguration() {
        return this.jobBuilderFactory.get("StepConfiguration")
                .start(StepConfigurationStep1())
                .next(StepConfigurationStep2())
                .build();
    }

    @Bean
    public Step StepConfigurationStep1() {
        return stepBuilderFactory.get("StepExampleStep1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        log.info("step1 has executed");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step StepConfigurationStep2() {
        return stepBuilderFactory.get("StepExampleStep2")
                .tasklet((contribution, chunkContext) -> {
                    log.info("step2 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
