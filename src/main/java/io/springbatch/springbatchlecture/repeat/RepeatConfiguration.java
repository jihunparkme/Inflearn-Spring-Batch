package io.springbatch.springbatchlecture.repeat;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.*;
import org.springframework.batch.repeat.CompletionPolicy;
import org.springframework.batch.repeat.RepeatCallback;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.repeat.exception.SimpleLimitExceptionHandler;
import org.springframework.batch.repeat.policy.CompositeCompletionPolicy;
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy;
import org.springframework.batch.repeat.policy.TimeoutTerminationPolicy;
import org.springframework.batch.repeat.support.RepeatTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class RepeatConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job repeatConfigurationJob() throws Exception {
        return jobBuilderFactory.get("repeatConfigurationJob")
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
                    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                        i++;
                        return i > 3 ? null : "item" + i;
                    }
                })
                .processor(new ItemProcessor<String, String>() {

                    RepeatTemplate template = new RepeatTemplate();

                    @Override
                    public String process(String item) throws Exception {

                        /**
                         * SimpleCompletionPolicy
                         * count ?????? chunkSize ????????? ????????? ?????? ?????? ????????? ??????(????????? ????????? count ????????? ?????? 1??? ??????)
                         */
                        template.setCompletionPolicy(new SimpleCompletionPolicy(2));

                        /**
                         * TimeoutTerminationPolicy
                         * ????????? ????????? ????????? ???????????? ??? ?????? ????????? ?????? (CompletionPolicy ??? ???????????? ?????? ??? ???????????? ????????? Policy ??????)
                         */
                        template.setCompletionPolicy(new TimeoutTerminationPolicy(3000));

                        /**
                         * CompositeCompletionPolicy
                         * ?????? ????????? CompletionPolicy ??? ??????????????? ?????? (?????? ??? ?????? ?????? ????????? ???????????? CompletionPolicy ??? ?????? ???????????? ??????)
                         */
                        CompositeCompletionPolicy completionPolicy = new CompositeCompletionPolicy();
                        CompletionPolicy[] completionPolicies = new CompletionPolicy[]{new TimeoutTerminationPolicy(3000),new SimpleCompletionPolicy(2)};
                        completionPolicy.setPolicies(completionPolicies);
                        template.setCompletionPolicy(completionPolicy);

                        /**
                         * simpleLimitExceptionHandler
                         * ?????? ?????? ???????????? ????????? ??????
                         */
                        template.setExceptionHandler(simpleLimitExceptionHandler());

                        template.iterate(new RepeatCallback() {
                            public RepeatStatus doInIteration(RepeatContext context) {
                                // ???????????? ?????? ?????? ..
                                System.out.println("repeatTest");
//                                throw new RuntimeException("Exception is occurred");
                                return RepeatStatus.CONTINUABLE;
                            }

                        });

                        return item;
                    }
                })
                .writer(new ItemWriter<String>() {
                    @Override
                    public void write(List<? extends String> items) throws Exception {
                        System.out.println(items);
                    }
                })
                .build();
    }

    @Bean
    public SimpleLimitExceptionHandler simpleLimitExceptionHandler() {
        return new SimpleLimitExceptionHandler(3);
    }
}