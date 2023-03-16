package io.springbatch.springbatchlecture.listener;

import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;

@Component
public class CustomItemProcessListener implements ItemProcessListener<Integer, String> {

    /**
     *   process() 메소드 호출 전 호출
     */
    @Override
    public void beforeProcess(Integer item) {
        System.out.println(">> beforeProcess");
    }

    /**
     *  process() 메소드 호출 성공 시 호출
     */
    @Override
    public void afterProcess(Integer item, String result) {
        System.out.println(">> afterProcess : "+ item);
        System.out.println(">> afterProcess : "+ result);
    }

    /**
     *  처리 도중 오류가 발생하면 호출
     */
    @Override
    public void onProcessError(Integer item, Exception e) {
        System.out.println(">> onProcessError : " + e.getMessage());
        System.out.println(">> onProcessError : " + item);
    }
}
