package io.springbatch.springbatchlecture.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;

@Slf4j
public class CustomAnnotationJobExecutionListener {

    @BeforeJob
    public void beforeJob(JobExecution JobExecution) {
        log.info("job is started");
        log.info("JobExecution.getJobName() : {}", JobExecution.getJobInstance().getJobName());
    }

    @AfterJob
    public void afterJob(JobExecution JobExecution) {
        long startTime = JobExecution.getStartTime().getTime();
        long endTime = JobExecution.getEndTime().getTime();
        log.info("총 소요시간 : {}", (endTime - startTime));

        log.info("JobExecution.getStatus() : {}", JobExecution.getStatus());
    }
}