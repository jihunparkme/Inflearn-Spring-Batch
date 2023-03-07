package io.springbatch.springbatchlecture.retry;

import io.springbatch.springbatchlecture.chunk.custom.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class RetryConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job retryConfigurationJob() throws Exception {
        return jobBuilderFactory.get("retryConfigurationJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    public Step step1() throws Exception {
        return stepBuilderFactory.get("step1")
                .<String, Customer>chunk(5)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .faultTolerant()
                    .skip(RetryableException.class) // Retry 에서 다시 발생하는 예외를 보완
                    .skipLimit(2)
                    .retryPolicy(limitCheckingItemSkipPolicy()) // 아래와 동일하게 동작
//                    .retry(RetryableException.class)
//                    .retryLimit(2)
                .build();
    }

    public SimpleRetryPolicy limitCheckingItemSkipPolicy() {
        Map<Class<? extends Throwable>, Boolean> exceptionClass = new HashMap<>();
        exceptionClass.put(RetryableException.class, true);

        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy(2, exceptionClass);

        return simpleRetryPolicy;
    }

    public ListItemReader<String> reader() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            items.add(String.valueOf(i));
        }
        return new ListItemReader<>(items);
    }

    public ItemProcessor processor() {
        RetryItemProcessor processor = new RetryItemProcessor();
        return processor;
    }

    public ItemWriter writer() {
        RetryItemWriter writer = new RetryItemWriter();
        return writer;
    }
}
