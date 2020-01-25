package org.uma.platform.reactive.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class RxApplication {

    public static void main(String[] args) {
        SpringApplication.run(RxApplication.class, args);

    }

    @Bean
    public RouterFunction<ServerResponse> routes() {
        return route(GET("/"), req -> ok()
                .body(Flux.just("Hello", "WebFlux"), String.class));
    }

}
