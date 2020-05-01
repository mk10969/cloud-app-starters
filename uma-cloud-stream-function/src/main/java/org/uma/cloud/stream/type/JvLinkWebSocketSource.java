package org.uma.cloud.stream.type;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import org.uma.cloud.stream.StreamFunctionProperties;
import reactor.core.publisher.Mono;
import reactor.core.publisher.UnicastProcessor;

import java.net.URI;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class JvLinkWebSocketSource {

    @Autowired
    private WebSocketClient webSocketClient;

    @Autowired
    private StreamFunctionProperties properties;

    @Getter
    private final UnicastProcessor<String> processor =
            UnicastProcessor.create(new ConcurrentLinkedQueue<>());


    public Mono<Void> connectToJvLinkWebSocket() {
        return webSocketClient.execute(URI.create(properties.getJvLinkWebSocketUrl()), this::handler);
    }


    private Mono<Void> handler(WebSocketSession session) {
        return session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .doOnNext(processor::onNext)
                .then();
    }
}
