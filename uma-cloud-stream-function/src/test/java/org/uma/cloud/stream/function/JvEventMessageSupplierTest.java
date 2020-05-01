package org.uma.cloud.stream.function;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class JvEventMessageSupplierTest {


    @Test
    void test_UnicastProcessor() throws InterruptedException {

        UnicastProcessor<Long> hotSource = UnicastProcessor.create(new ConcurrentLinkedQueue<>());

        Flux<Long> hotPublisher = hotSource.publish().autoConnect()
                .publishOn(Schedulers.parallel());

//        Flux<Long> hotPublisher2 = hotSource.publish().autoConnect()
//                .publishOn(Schedulers.parallel());


        hotPublisher.subscribe(i -> System.out.println("TEST1: " + i));
        // Duplicate Subscriptionは、OK
        hotPublisher.subscribe(i -> System.out.println("TEST1-1: " + i));

//        // 2つ目のsubscriberになるので、throw
//        hotPublisher2.subscribe(i -> System.out.println("TEST2: " + i));

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(() -> hotSource.onNext(10000L),
                2, 1, TimeUnit.SECONDS);
        ScheduledExecutorService service2 = Executors.newSingleThreadScheduledExecutor();
        service2.scheduleWithFixedDelay(() -> hotSource.onNext(20000L),
                2, 1, TimeUnit.SECONDS);


        Thread.sleep(1000000L);
    }

    @Test
    void test_EmitterProcessor() throws InterruptedException {

        EmitterProcessor<Long> hotSource = EmitterProcessor.create();

        hotSource.subscribe(i -> System.out.println("TEST1: " + i));
        // Duplicate Subscription
        hotSource.subscribe(i -> System.out.println("TEST1-1: " + i));

        // 2つ目のsubscriberになるので、throw
//        hotPublisher2.subscribe(i -> System.out.println("TEST2: " + i));

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(() -> hotSource.onNext(10000L),
                2, 1, TimeUnit.SECONDS);

        ScheduledExecutorService service2 = Executors.newSingleThreadScheduledExecutor();
        service2.scheduleWithFixedDelay(() -> hotSource.onNext(20000L),
                2, 1, TimeUnit.SECONDS);


        Thread.sleep(1000000L);
    }

}