package io.springbatch.springbatchlecture.step;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.job.DefaultJobParametersExtractor;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class JobStepConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobLauncher jobLauncher;

    @Bean
    public Job parentJob() {
        return this.jobBuilderFactory.get("parentJob")
                .start(JobStepConfigurationJobStep(null))
                .next(JobStepConfigurationStep2())
                .build();
    }

    public Step JobStepConfigurationJobStep(JobLauncher jobLauncher) {
        return this.stepBuilderFactory.get("JobStepConfigurationJobStep")
                .job(childJob())
                .launcher(jobLauncher)
                .listener(new StepExecutionListener() {
                    @Override
                    public void beforeStep(StepExecution stepExecution) {
                        stepExecution.getExecutionContext().putString("name", "user1");
                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        return null;
                    }
                })
                .parametersExtractor(jobParametersExtractor())
                .build();
    }

    public Job childJob() {
        return this.jobBuilderFactory.get("JobStepConfigurationChildJob")
                .start(JobStepConfigurationStep1())
                .build();
    }

    public Step JobStepConfigurationStep1() {
        return stepBuilderFactory.get("step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//                        throw new RuntimeException("step1 was failed");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    public Step JobStepConfigurationStep2() {
        return stepBuilderFactory.get("step2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//                        throw new RuntimeException("step2 was failed");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    public DefaultJobParametersExtractor jobParametersExtractor() {
        DefaultJobParametersExtractor extractor = new DefaultJobParametersExtractor();
        extractor.setKeys(new String[]{"name"});
        return extractor;
    }
}