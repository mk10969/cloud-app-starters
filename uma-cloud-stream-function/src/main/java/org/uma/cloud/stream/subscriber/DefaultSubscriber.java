package org.uma.cloud.stream.subscriber;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;

@Slf4j
public class DefaultSubscriber<T> implements org.reactivestreams.Subscriber<T> {

    @Override
    public void onSubscribe(Subscription s) {
        log.info("{}", s);
    }

    @Override
    public void onNext(T item) {
        // no ops
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("ERROR", throwable);
    }

    @Override
    public void onComplete() {
        log.info("Complete!!!");
    }
}
