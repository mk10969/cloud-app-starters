package org.uma.cloud.stream.util;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

public class FluxErrorTest {

    @Test
    void test_onErrorResume() throws InterruptedException {
        source()
                .onErrorResume(t -> Mono.just(10))
                .subscribe(System.out::println);
        Thread.sleep(3000L);
    }

    @Test
    void test_onErrorReturn() throws InterruptedException {
        source()
                .onErrorReturn(100)
                .subscribe(System.out::println);
        Thread.sleep(3000L);
    }


    @Test
    void test_error() throws InterruptedException {
        source()
                .subscribe(System.out::println, e -> System.out.println(e));
        Thread.sleep(3000L);
    }

    @Test
    void test_onErrorContinue() throws InterruptedException {
        source()
                .onErrorContinue((e, o) -> System.out.println(e + ":" + o))
                .subscribe(System.out::println);
        Thread.sleep(3000L);
    }


    private Flux<Integer> source() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        return Flux.fromIterable(list)
                .map(i -> {
                    if (i == 5) {
                        throw new IllegalArgumentException("あかん");
                    } else {
                        return i;
                    }
                });
    }

}
