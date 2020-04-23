package org.uma.cloud.stream.function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
public class JvWatchEventFunction {

    @Bean
    public Function<Flux<String>, Mono<Void>> JvWatchEventIdSubscribe() {
        return eventId -> eventId
                .then();
    }


}
