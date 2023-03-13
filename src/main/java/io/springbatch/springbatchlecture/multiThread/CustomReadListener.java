package io.springbatch.springbatchlecture.multiThread;

import io.springbatch.springbatchlecture.async.Customer;
import org.springframework.batch.core.ItemReadListener;

public class CustomReadListener implements ItemReadListener<Customer> {

    @Override
    public void beforeRead() {
    }

    @Override
    public void afterRead(Customer item) {
        System.out.println("Thread : " + Thread.currentThread().getName() + ", read item : " + item.getId());
    }

    @Override
    public void onReadError(Exception ex) {

    }
}