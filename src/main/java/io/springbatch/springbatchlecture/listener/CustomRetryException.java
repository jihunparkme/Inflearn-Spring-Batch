package io.springbatch.springbatchlecture.listener;

public class CustomRetryException extends Exception {

    public CustomRetryException() {
        super();
    }

    public CustomRetryException(String message) {
        super(message);
    }
}
