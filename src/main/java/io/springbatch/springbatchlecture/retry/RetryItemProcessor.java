package io.springbatch.springbatchlecture.retry;

import io.springbatch.springbatchlecture.chunk.custom.Customer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.classify.Classifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.classify.BinaryExceptionClassifier;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.DefaultRetryState;
import org.springframework.retry.support.RetryTemplate;

public class RetryItemProcessor implements ItemProcessor<String, Customer> {

    @Autowired
    private RetryTemplate retryTemplate;

    private int cnt;

    @Override
    public Customer process(String item) throws Exception {

        Classifier<Throwable, Boolean> rollbackClassifier = new BinaryExceptionClassifier(true);

        Customer result = retryTemplate.execute(new RetryCallback<Customer, RuntimeException>() {
                                                    @Override
                                                    public Customer doWithRetry(RetryContext context) throws RuntimeException {
                                                        // 설정된 조건 및 횟수(2)만큼 재시도 수행
                                                        if (item.equals("1") || item.equals("2")) {
                                                            cnt++;
                                                            throw new RetryableException("failed cnt : " + cnt);
                                                        }
                                                        return new Customer(item);
                                                    }
                                                }, new RecoveryCallback<Customer>() {
                                                    @Override
                                                    public Customer recover(RetryContext context) throws Exception {
                                                        // 재시도 모두 소진 시 수행
                                                        return new Customer(item);
                                                    }
                                                }
                /**
                 * [Skip, retryState 미설정] RecoveryCallback 을 통해 모든 아이템 출력
                 *  - retryState 가 없을 경우 chunk 처음 단계로 가지 않고 이어서 수행
                 * [Skip, retryState 설정]
                 *  - retryState 가 있을 경우 chunk 단계의 처음으로
                 *  - skip 설정도 있으므로 1, 2 를 제외한 아이템 노출
                 */
                , new DefaultRetryState(item, rollbackClassifier)
        );
        //template - state 추가, skip 추가, backoff 추가,
        return result;
    }
}