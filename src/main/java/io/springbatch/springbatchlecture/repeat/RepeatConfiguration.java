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
                         * count 값이 chunkSize 값보다 크거나 같을 경우 반복문 종료(반복할 때마다 count 변수의 값을 1씩 증가)
                         */
                        template.setCompletionPolicy(new SimpleCompletionPolicy(2));

                        /**
                         * TimeoutTerminationPolicy
                         * 소요된 시간이 설정된 시간보다 클 경우 반복문 종료 (CompletionPolicy 를 중첩으로 세팅 시 마지막에 세팅한 Policy 적용)
                         */
                        template.setCompletionPolicy(new TimeoutTerminationPolicy(3000));

                        /**
                         * CompositeCompletionPolicy
                         * 여러 유형의 CompletionPolicy 를 복합적으로 처리 (여러 개 중에 먼저 조건이 부합하는 CompletionPolicy 에 따라 반복문이 종료)
                         */
                        CompositeCompletionPolicy completionPolicy = new CompositeCompletionPolicy();
                        CompletionPolicy[] completionPolicies = new CompletionPolicy[]{new TimeoutTerminationPolicy(3000),new SimpleCompletionPolicy(2)};
                        completionPolicy.setPolicies(completionPolicies);
                        template.setCompletionPolicy(completionPolicy);

                        /**
                         * simpleLimitExceptionHandler
                         * 예외 제한 횟수만큼 반복문 실행
                         */
                        template.setExceptionHandler(simpleLimitExceptionHandler());

                        template.iterate(new RepeatCallback() {
                            public RepeatStatus doInIteration(RepeatContext context) {
                                // 비즈니스 로직 구현 ..
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