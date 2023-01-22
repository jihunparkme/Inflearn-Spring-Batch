package io.springbatch.springbatchlecture.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //=> 하나의 배치 잡을 정의하고 빈 설정
@RequiredArgsConstructor
public class HelloJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory; //=> Job을 생성
    private final StepBuilderFactory stepBuilderFactory; //=> Step을 생성

    @Bean
    public Job helloJob() {
        return this.jobBuilderFactory.get("helloJob") //=> Job 생성 (일, 일감)
                .start(helloStep1())
                .next(helloStep2())
                .build();
    }

    @Bean
    public Step helloStep1() {
        return stepBuilderFactory.get("helloStep1") //=> Step 생성 (일의 항목, 단계)
                .tasklet(new Tasklet() { //=> Step 안에서 단일 Task로 수행되는 로직 구현 (익명 클래스), (작업 내용)
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println(" ============================");
                        System.out.println(" >> Hello Spring Batch");
                        System.out.println(" ============================");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    public Step helloStep2() {
        return stepBuilderFactory.get("helloStep2") //=> Step 생성
                .tasklet((contribution, chunkContext) -> { //=> Step 안에서 단일 Task로 수행되는 로직 구현 (람다)
                    System.out.println(" ============================");
                    System.out.println(" >> Step2 has executed");
                    System.out.println(" ============================");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
