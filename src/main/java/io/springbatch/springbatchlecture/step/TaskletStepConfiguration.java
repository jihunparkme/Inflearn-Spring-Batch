package io.springbatch.springbatchlecture.step;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.*;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
public class TaskletStepConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job TaskletStepConfiguration() {
        return this.jobBuilderFactory.get("TaskletStepConfiguration")
                .start(TaskletStepConfigurationTaskStep())
                .next(TaskletStepConfigurationChunkStep())
                .build();
    }

    @Bean
    public Step TaskletStepConfigurationTaskStep() {
        return stepBuilderFactory.get("taskStep")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step1 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step TaskletStepConfigurationChunkStep() {
        return stepBuilderFactory.get("chunkStep")
                .<String, String>chunk(3)
                .reader(new ListItemReader(Arrays.asList("item1", "item2", "item3")))
                .processor(new ItemProcessor<String, String>() {
                    @Override
                    public String process(String item) throws Exception {
                        return item.toUpperCase();
                    }
                })
                .writer(list -> {
                    list.forEach(item -> System.out.println(item));
                })
                .build();
    }

}