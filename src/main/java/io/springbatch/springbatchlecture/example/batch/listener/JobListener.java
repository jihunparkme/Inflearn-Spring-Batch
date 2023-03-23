package io.springbatch.springbatchlecture.example.batch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * <pre>
 * io.anymobi.core.batch.listener.job
 * ㄴ DataSendJobListener.java
 * </pre>
 * 배치 Job 이 실행되면 호출되는 JobExecutionListener
 **/

public class JobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        long time = jobExecution.getEndTime().getTime() - jobExecution.getStartTime().getTime();
        System.out.println("총 소요시간 : " + time);
    }
}
