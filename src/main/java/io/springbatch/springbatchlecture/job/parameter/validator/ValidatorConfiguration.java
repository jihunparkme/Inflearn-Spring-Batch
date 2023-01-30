package io.springbatch.springbatchlecture.job.parameter.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class ValidatorConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job ValidatorConfiguration() {
        return this.jobBuilderFactory.get("ValidatorConfiguration")
                /** JobParametersValidator 구현 **/
                .validator(new CustomJobParametersValidator())
                /** DefaultJobParametersValidator 사용 **/
//                .validator(new DefaultJobParametersValidator(new String[]{"name", "date"}, new String[]{"count"}))
                .start(ValidatorConfigurationStep1())
                .next(ValidatorConfigurationStep2())
                .next(ValidatorConfigurationStep3())
                .build();
    }

    @Bean
    public Step ValidatorConfigurationStep1() {
        return stepBuilderFactory.get("ValidatorConfigurationStep1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step1 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step ValidatorConfigurationStep2() {
        return stepBuilderFactory.get("ValidatorConfigurationStep2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step2 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step ValidatorConfigurationStep3() {
        return stepBuilderFactory.get("ValidatorConfigurationStep3")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step3 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}