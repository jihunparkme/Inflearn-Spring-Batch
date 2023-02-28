package io.springbatch.springbatchlecture.chunk;

import io.springbatch.springbatchlecture.chunk.custom.CustomItemProcessor;
import io.springbatch.springbatchlecture.chunk.custom.CustomItemReader;
import io.springbatch.springbatchlecture.chunk.custom.CustomItemWriter;
import io.springbatch.springbatchlecture.chunk.custom.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
public class ItemReader_ItemProcessor_ItemWriter_Configuration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job ItemReader_ItemProcessor_ItemWriter_Configuration() {
        return jobBuilderFactory.get("ItemReader_ItemProcessor_ItemWriter_Configuration")
                .start(step1())
                .next(step2())
                .build();
    }

    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<String, String>chunk(3)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    public ItemReader itemReader() {
        return new CustomItemReader(Arrays.asList(new Customer("user1"), new Customer("user2"), new Customer("user3")));
    }

    public ItemProcessor itemProcessor() {
        return new CustomItemProcessor();
    }

    public ItemWriter itemWriter() {
        return new CustomItemWriter();
    }

    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step2 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
