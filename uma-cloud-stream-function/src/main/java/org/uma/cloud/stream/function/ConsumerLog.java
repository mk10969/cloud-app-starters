package org.uma.cloud.stream.function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@Configuration
public class ConsumerLog {

    /**
     * reactive consumer
     * ちょっと特殊です。
     * https://cloud.spring.io/spring-cloud-static/spring-cloud-stream/3.0.3.RELEASE/reference/html/spring-cloud-stream.html#_consumer_reactive
     */
//    @Bean
//    public Function<Flux<String>, Mono<Void>> log() {
//        return flux -> flux.doOnNext(System.out::println).then();
//    }


    /**
     * 下記いける！
     */
    @Bean
    public Consumer<String> log() {
        return i -> System.out.println("TEST: " + i);
    }

//    /**
//     * subscribeするの忘れないこと！
//     */
//    @Bean
//    public Consumer<Flux<?>> log() {
//        return data -> data
//                .doOnNext(i -> System.out.println("TEST: " + i))
//                .subscribe();
//    }

}
