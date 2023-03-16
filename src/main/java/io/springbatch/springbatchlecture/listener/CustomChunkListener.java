package io.springbatch.springbatchlecture.listener;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.AfterChunkError;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

@Component
public class CustomChunkListener {

    private int i; // 공유 데이터

    /**
     * 트랜잭션 시작 전 호출
     * ItemReader.read() 메소드 호출 전
     */
    @BeforeChunk
    public void beforeChunk(ChunkContext context) {
        System.out.println(">> Before the chunk : "+ context.getStepContext().getStepExecution().getStepName());
        Object count = context.getStepContext().getStepExecution().getExecutionContext().get("count");
        if(count == null){
            context.getStepContext().getStepExecution().getExecutionContext().putInt("count", ++i);
        }
    }

    /**
     * Chunk 커밋 후 호출
     * ItemWriter.write() 메소드 호출 후
     * 롤백 되었다면 호출되지 않음
     */
    @AfterChunk
    public void afterChunk(ChunkContext context) {
        System.out.println(">> After the chunk : "+ context.getStepContext().getStepExecution().getStepName());
        int count = (int)context.getStepContext().getStepExecution().getExecutionContext().get("count");
        System.out.println(">> count : "+ count);
    }

    /**
     *  오류 발생 및 롤백이 되면 호출
     */
    @AfterChunkError
    public void afterChunkError(ChunkContext context) {
        System.out.println(">> After chunk error : "+ context.getStepContext().getStepExecution().getStepName());
        int count = (int)context.getStepContext().getStepExecution().getExecutionContext().get("count");
        System.out.println(">> count : "+ count);
    }
}
