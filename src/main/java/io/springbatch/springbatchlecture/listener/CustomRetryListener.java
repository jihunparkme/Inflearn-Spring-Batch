package io.springbatch.springbatchlecture.listener;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.stereotype.Component;

public class CustomRetryListener implements RetryListener {

    /**
     * 재시도 전 매번 호출, false 반환 시 retry 생략
     */
    @Override
    public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
        System.out.println("open");
        return true;
    }

    /**
     * 재시도 후 매번 호출
     */
    @Override
    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        System.out.println("close");
    }

    /**
     * 재시도 실패 시마다 호출
     */
    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        System.out.println("Retry Count: " + context.getRetryCount());
    }
}
