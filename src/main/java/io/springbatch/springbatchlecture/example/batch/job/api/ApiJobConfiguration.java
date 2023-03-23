package io.springbatch.springbatchlecture.example.batch.job.api;

import io.springbatch.springbatchlecture.example.batch.listener.JobListener;
import io.springbatch.springbatchlecture.example.batch.tasklet.ApiEndTasklet;
import io.springbatch.springbatchlecture.example.batch.tasklet.ApiStartTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApiJobConfiguration {

    /**
     * Job-2
     * 기능
     * • DB 로부터 데이터를 읽어서 API 서버와 통신
     * 내용
     * • Partitioning 기능을 통한 멀티 스레드 구조로 Chunk 기반 프로세스 구현
     * • 제품의 유형에 따라서 서로 다른 API 통신을 하도록 구성(ClassifierCompositerItemWriter)
     * • API 서버는 3개로 구성하여 요청을 처리
     * • 제품내용과 API 통신 결과를 각 파일로 저장(FlatFileWriter 상속)
     */

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ApiStartTasklet apiStartTasklet;
    private final ApiEndTasklet apiEndTasklet;
    private final Step jobStep;

    @Bean
    public Job apiJob() throws Exception {

        return jobBuilderFactory.get("apiJob")
                .incrementer(new RunIdIncrementer())
                .listener(new JobListener())
                .start(apiStep1())
                .next(jobStep)
                .next(apiStep2())
                .build();
    }

    @Bean
    public Step apiStep1() throws Exception {
        return stepBuilderFactory.get("apiStep")
                .tasklet(apiStartTasklet)
                .build();
    }

    @Bean
    public Step apiStep2() throws Exception {
        return stepBuilderFactory.get("apiStep2")
                .tasklet(apiEndTasklet)
                .build();
    }
}