package io.springbatch.springbatchlecture.job.launcher;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class JobLaunchingUnSyncController {

    @Autowired
    private Job job;

    @Autowired
    private BasicBatchConfigurer basicBatchConfigurer;

    @PostMapping(value = "/batch")
    public String launch(@RequestBody Member member) throws Exception {

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("id", member.getId())
                .addDate("date", new Date())
                .toJobParameters();
        /**
         * @Autowired private JobLauncher simpleLauncher; 의 경우
         * JobLauncher는 Proxy 객체이므로 타입 캐스팅이 불가
         */
        // SimpleJobLauncher jobLauncher = (SimpleJobLauncher)simpleLauncher;

        /**
         * @Autowired private BasicBatchConfigurer basicBatchConfigurer; 는
         * 실제 JobLauncher 객체를 가지고 있음
         */
        SimpleJobLauncher jobLauncher = (SimpleJobLauncher) basicBatchConfigurer.getJobLauncher();
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        jobLauncher.run(job, jobParameters);

        return "batch completed";
    }
}
