package io.springbatch.springbatchlecture.repeat;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.*;
import org.springframework.batch.repeat.exception.SimpleLimitExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class FaultTolerantConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job faultTolerantConfigurationJob() throws Exception {
        return jobBuilderFactory.get("faultTolerantConfigurationJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    public Step step1() throws Exception {
        return stepBuilderFactory.get("step1")
                .<String, String>chunk(5)
                .reader(new ItemReader<String>() {
                    int i = 0;

                    @Override
                    public String read() {
                        i++;
                        if (i == 1) {
                            throw new IllegalArgumentException("skip");
                        }
                        return i > 3 ? null : "item" + i;
                    }
                })
                .processor((ItemProcessor<String, String>) item -> {
                    throw new IllegalStateException("retry");
//                    return item;
                })
                .writer(items -> System.out.println(items))
                .faultTolerant()
                    .skip(IllegalArgumentException.class)
                    .skipLimit(1)
                    .retry(IllegalStateException.class)
                    .retryLimit(2)
                .build();
    }

    @Bean
    public SimpleLimitExceptionHandler simpleLimitExceptionHandler() {
        return new SimpleLimitExceptionHandler(3);
    }
}
