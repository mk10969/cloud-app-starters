package org.uma.cloud.stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.uma.cloud.stream.function.JvRaceSupplier;
import org.uma.cloud.stream.model.EventMessage;
import org.uma.cloud.stream.type.JvLinkWebSocketSource;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

@Configuration
@EnableConfigurationProperties(StreamFunctionProperties.class)
public class StreamFunctionConfiguration {

    @Slf4j
    @Configuration
    public static class DynamicController {

        @Autowired
        private JvRaceSupplier jvRaceSupplier;

        @Autowired
        private JvLinkWebSocketSource source;


        @Bean
        RouterFunction<ServerResponse> routes() {
            return RouterFunctions
                    .route(RequestPredicates.GET("/"), this::ping)
                    .andRoute(RequestPredicates.POST("/raceId"), this::putRaceId)
                    .andRoute(RequestPredicates.POST("/eventId"), this::putEventId)
                    .filter((request, next) -> {
                        // routes共通
                        try {
                            return next.handle(request);
                        } catch (Exception e) {
                            log.error("Server Error: ", e);
                            // 500
                            return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                        }
                    });
        }

        @NotNull
        private Mono<ServerResponse> ping(ServerRequest request) {
            return Mono.just("ping!")
                    .flatMap(text -> ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromValue(text)));
        }

        /**
         * bodyのバリデーションは行わない。
         */
        @NotNull
        private Mono<ServerResponse> putRaceId(ServerRequest request) {
            return request.bodyToMono(String.class)
                    .doOnNext(i -> jvRaceSupplier.getProcessor().onNext(i))
                    .flatMap(body -> ServerResponse.accepted()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromValue(body)));
        }

        /**
         * bodyのバリデーションは行わない。
         */
        @NotNull
        private Mono<ServerResponse> putEventId(ServerRequest request) {
            return request.bodyToMono(EventMessage.class)
                    .doOnNext(i -> source.getProcessor().onNext(i))
                    .flatMap(body -> ServerResponse.accepted()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromValue(body)));
        }
    }

}
