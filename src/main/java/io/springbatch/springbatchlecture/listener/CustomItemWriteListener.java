package io.springbatch.springbatchlecture.listener;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomItemWriteListener implements ItemWriteListener<String> {

    /**
     *  write() 메소드 호출 전 호출
     */
    @Override
    public void beforeWrite(List<? extends String> items) {
        System.out.println(">> beforeWrite");
    }

    /**
     *  write() 메소드 호출 성공 시 호출
     */
    @Override
    public void afterWrite(List<? extends String> items) {
        System.out.println(">> afterWrite : "+ items);
    }

    /**
     *  쓰기 도중 오류 발생 시 호출
     */
    @Override
    public void onWriteError(Exception exception, List<? extends String> items) {
        System.out.println(">> onWriteError : " + exception.getMessage());
        System.out.println(">> onWriteError : " + items);
    }
}