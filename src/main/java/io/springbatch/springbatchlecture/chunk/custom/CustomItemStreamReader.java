package io.springbatch.springbatchlecture.chunk.custom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;

import java.util.List;

@Slf4j
public class CustomItemStreamReader implements ItemStreamReader<String> {

    private final List<String> items;
    private int index = -1;
    private boolean restart = false;

    public CustomItemStreamReader(List<String> items) {
        this.items = items;
        this.index = 0;
    }

    @Override
    public String read() throws Exception {
        String item = null;

        if (this.index < this.items.size()) {
            item = this.items.get(index);
            index++;
        }

        if (this.index == 6 && !restart) {
            log.error("Restart is required.");
        }

        return item;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        if (executionContext.containsKey("index")) { // 재시작
            index = executionContext.getInt("index"); // DB 에서 index 정보 조회
            this.restart = true;
        } else { // 처음 시작
            index = 0;
            executionContext.put("index", index); // DB 저장
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        executionContext.put("index", index); // 실시간으로 최종 상태 정보 저장
    }

    @Override
    public void close() throws ItemStreamException {
        log.info("close");
    }
}
