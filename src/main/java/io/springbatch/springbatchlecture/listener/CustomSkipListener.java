package io.springbatch.springbatchlecture.listener;

import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

public class CustomSkipListener implements SkipListener<Integer, String> {

    /**
     * read 수행중 Skip 발생 시 호출
     */
    @Override
    public void onSkipInRead(Throwable t) {
        System.out.println(">> onSkipInRead : " + t.getMessage());
    }

    /**
     * write 수행중 Skip 발생 시 호출
     */
    @Override
    public void onSkipInWrite(String item, Throwable t) {
        System.out.println(">> onSkipInWrite : " + item);
		System.out.println(">> onSkipInWrite : "+ t.getMessage());
    }

    /**
     * process 수행중 Skip 발생 시 호출
     */
    @Override
    public void onSkipInProcess(Integer item, Throwable t) {
        System.out.println(">> onSkipInProcess : " + item);
        System.out.println(">> onSkipInProcess : " + t.getMessage());
    }
}
