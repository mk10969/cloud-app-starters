package org.uma.jvLink.server.component;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertAll;

class JvSetupReactiveCommandLineRunnerTest {

    @Test
    void test() {
        assertAll();
    }


    @Test
    void test2() throws InterruptedException {
        Flux.just(1, 2, 3, 4, 5)
                .flatMap(i -> addition(i))
                .log()
                .subscribe();


        Thread.sleep(1000L);
    }

    private Flux<Tuple2<String, Integer>> addition(Integer num) {
        return Flux.fromIterable(Arrays.asList(4, 4, 4, 4, 4))
                .map(i -> i + num)
                .map(i -> Tuples.of("addition" , i));
    }

}