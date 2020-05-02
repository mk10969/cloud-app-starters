package org.uma.cloud.stream.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.stream.model.EventMessage;
import org.uma.cloud.stream.type.JvLinkWebSocketSource;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Slf4j
@Configuration
public class JvEventSupplier {

    @Autowired
    private JvLinkWebSocketSource source;

    @Bean
    @ConditionalOnProperty(prefix = "uma.stream.init", name = "enabled", havingValue = "true")
    public CommandLineRunner websocketSubscribe() {
        return args -> this.source
                .connectToJvLinkWebSocket()
                .doOnSubscribe(i -> log.info("jvWatchEvent start !!!"))
                .subscribe();
    }


    @Bean
    public Supplier<Flux<EventMessage>> JvWatchEventId() {
        return () -> this.source.getProcessor()
                .publish()
                .autoConnect()
                .log();
    }


//    @Bean
//    @ConditionalOnProperty(prefix = "spring.init", name = "enabled", havingValue = "true")
//    public CommandLineRunner websocketSubscribe() {
//        return args -> {
//            Flux.interval(Duration.ofSeconds(1))
//                    .doOnNext(i -> processor.onNext("WH:202004260812"))
//                    .log()
//                    .subscribe();
//        };
//    }

}
