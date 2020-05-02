package org.uma.cloud.stream.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class DefaultConsumer {

    /**
     * subscriber
     */
    @Bean
    public Consumer<Flux<String>> subscribe() {
        return data -> data
                .doOnNext(i -> log.info("Consumer1: {}", i))
                .subscribe();
    }

    /**
     * subscriber2
     */
    @Bean
    public Consumer<Flux<String>> subscribe2() {
        return data -> data
                .doOnNext(i -> log.info("Consumer2: {}", i))
                .subscribe();
    }
}
