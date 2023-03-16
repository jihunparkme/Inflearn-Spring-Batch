package io.springbatch.springbatchlecture.listener;

import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

@Component
public class CustomItemReadListener implements ItemReadListener {

    /**
     * read() 메소드 호출 전 매번 호출
     */
    @Override
    public void beforeRead() {
        System.out.println(">> beforeRead"); // item 이 없을 때까지 반복하므로 최종 한번 더 호출된다
    }

    /**
     *  read() 메소드 호출 성공 때 마다 호출
     */
    @Override
    public void afterRead(Object item) {
        System.out.println(">> afterRead : "+ item);
    }

    /**
     *  읽는 도중 오류가 발생 시 호출
     */
    @Override
    public void onReadError(Exception ex) {
        System.out.println(">> onReadError : " + ex.getMessage());
    }
}
