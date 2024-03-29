package io.springbatch.springbatchlecture.multiThread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@EnableBatchProcessing
@SpringBatchTest
@ContextConfiguration(classes = {ParallelStepConfiguration.class})
public class ParallelStepConfigurationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    public void test() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("message", "hello")
                .toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        log.info("jobExecution = {}", jobExecution);
        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
    }
}