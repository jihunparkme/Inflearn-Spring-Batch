package io.springbatch.springbatchlecture.job.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobRepositoryListener implements JobExecutionListener {

    @Autowired
    private JobRepository jobRepository;

    @Override
    public void beforeJob(JobExecution jobExecution) {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();
        JobParameters jobParameters = new JobParametersBuilder().addString("requestDate", "20210102").toJobParameters();
        JobExecution lastExecution = jobRepository.getLastJobExecution(jobName, jobParameters);
        if(lastExecution != null) {
            for (StepExecution execution : lastExecution.getStepExecutions()) {
                BatchStatus status = execution.getStatus();
                log.info("BatchStatus = " + status.isRunning());
                log.info("BatchStatus = " + status.name());
                log.info("exitStatus = " + execution.getExitStatus());
                log.info("stepName = " + execution.getStepName());
            }
        }
    }
}
