package io.springbatch.springbatchlecture.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBatchTest // ApplicationContext 테스트에 필요한 여러 유틸 Bean 등록
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SimpleJobConfiguration.class, TestBatchConfig.class}) // Job 설정 클래스 지정. 통합 테스트를 위한 여러 의존성 빈들을 주입
public class SimpleJobConfigurationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void launchJob() throws Exception {

        // given
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("requestDate", "20020101")
                .addLong("date", new Date().getTime())
                .toJobParameters();

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters); // Job 을 실행시키고 JobExecution 반환

        // then
        Assert.assertEquals(jobExecution.getStatus(), BatchStatus.COMPLETED);
        Assert.assertEquals(jobExecution.getExitStatus(), ExitStatus.COMPLETED);
    }


    @Test
    public void launchStep() throws Exception {

        // given
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("requestDate", "20020101")
                .addLong("date", new Date().getTime())
                .toJobParameters();

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("step1"); //  Step 을 실행시키고 JobExecution 반환
        StepExecution stepExecution = (StepExecution) ((List) jobExecution.getStepExecutions()).get(0);

        // then
        Assert.assertEquals(stepExecution.getCommitCount(), 11);
        Assert.assertEquals(stepExecution.getWriteCount(), 1000);
        Assert.assertEquals(stepExecution.getWriteCount(), 1000);
    }

    @After
    public void clear() throws Exception {
        jdbcTemplate.execute("delete from customer2");
    }
}