package io.springbatch.springbatchlecture.job.parameter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

//@Component
public class JobParameterTest implements ApplicationRunner {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job JobParameter;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", "user1")
                .addLong("seq", 1L)
                .addDate("date", new Date())
                .addDouble("age", 29.5)
                .toJobParameters();

        jobLauncher.run(JobParameter, jobParameters);
    }
}