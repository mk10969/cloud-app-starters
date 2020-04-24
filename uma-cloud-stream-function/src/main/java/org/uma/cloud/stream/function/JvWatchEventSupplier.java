package org.uma.cloud.stream.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import org.uma.cloud.stream.StreamFunctionProperties;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.UnicastProcessor;

import java.net.URI;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;

@Slf4j
@Configuration
public class JvWatchEventSupplier {

    @Autowired
    private WebSocketClient webSocketClient;

    @Autowired
    private StreamFunctionProperties properties;

//    private final EmitterProcessor<String>でもいいかも

    private final UnicastProcessor<String> processor =
            UnicastProcessor.create(new ConcurrentLinkedQueue<>());


    @Bean
    public Supplier<Flux<String>> JvWatchEventId() {
        return () -> processor.publish()
                .autoConnect()
                .doOnNext(JvWatchEventId ->
                        log.info("JvWatchEventId: {}", JvWatchEventId));
    }


    @Bean
    @ConditionalOnProperty(prefix = "spring.init", name = "enabled", havingValue = "true")
    public CommandLineRunner commandLineRunner() {
        return args -> this.connectToJvLinkWebSocket().subscribe();
    }

    private Mono<Void> connectToJvLinkWebSocket() {
        return webSocketClient.execute(
                URI.create(properties.getJvLinkWebSocketUrl()), this::handler);
    }

    private Mono<Void> handler(WebSocketSession session) {
        return session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .doOnNext(processor::onNext) // queueに入れる
                .doOnSubscribe(i -> log.info("jvWatchEvent start !!!"))
                .then();
    }

}
