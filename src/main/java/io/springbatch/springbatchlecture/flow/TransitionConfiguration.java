package io.springbatch.springbatchlecture.flow;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class TransitionConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job TransitionConfiguration() {
        return this.jobBuilderFactory.get("TransitionConfiguration")
                .start(step1())
                    .on("FAILED") // Step1() 종료상태(ExitStatus) 가 FAILED 이면
                    .to(step2()) // step2() 실행
                    .on("*") // Step2() 가 성공적으로 완료되면
                    .stop() // Step 종료상태와 상관없이 Job 중단
                .from(step1())
                    .on("*") // Step1() 종료상태(ExitStatus) 가 FAILED 가 아닌 모든 경우
                    .to(step5()) // step5 실행 (재정의)
                    .next(step6()) // Step5() 가 성공적으로 완료되면 step6() 실행
                    .on("COMPLETED") // Step6() 종료상태(ExitStatus) 가 COMPLETED 면
                    .end() // Job 종료
                .end()
                .build();
    }

    public Flow flow() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow");
        flowBuilder
                .start(step3())
                .next(step4())
                .end();
        return flowBuilder.build();
    }

    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(">> step1 has executed");
                    contribution.setExitStatus(ExitStatus.FAILED);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    public Step step2() {
        return stepBuilderFactory.get("step2")
                .flow(flow())
                .build();
    }

    public Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(">> step3 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    public Step step4() {
        return stepBuilderFactory.get("step4")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(">> step4 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    public Step step5() {
        return stepBuilderFactory.get("step5")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(">> step5 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    public Step step6() {
        return stepBuilderFactory.get("step6")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(">> step6 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}