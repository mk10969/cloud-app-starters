//package org.uma.platform.feed.application.handler;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.ServerRequest;
//import org.springframework.web.reactive.function.server.ServerResponse;
//import org.uma.platform.common.model.Ancestry;
//import org.uma.platform.common.model.BreedingHorse;
//import org.uma.platform.common.model.Offspring;
//import org.uma.platform.common.utils.lang.DateUtil;
//import org.uma.platform.feed.application.service.JvBloodService;
//import reactor.core.publisher.Mono;
//
//import java.time.LocalDateTime;
//
//import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
//import static org.springframework.web.reactive.function.server.RequestPredicates.path;
//import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
//import static org.springframework.web.reactive.function.server.RouterFunctions.route;
//
//@Component
//@RequiredArgsConstructor
//public class JvBloodHandler {
//
//    private final JvBloodService bloodService;
//
//    public RouterFunction<ServerResponse> routes() {
//        return nest(path("/blood"),
//                route(GET("/"), this::ping)
//                        .andRoute(GET("/ancestry/{epochSecond}"), this::ancestryUntil)
//                        .andRoute(GET("/breedingHorse/{epochSecond}"), this::breedingHorseUntil)
//                        .andRoute(GET("/offspring/{epochSecond}"), this::offspringUntil));
//    }
//
//
//    private Mono<ServerResponse> ping(ServerRequest request) {
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                .body(Mono.just("blood endpoint"), String.class);
//    }
//
//    private Mono<ServerResponse> ancestryUntil(ServerRequest request) {
//        LocalDateTime dateTime = DateUtil.tolocalDateTime(request.pathVariable("epochSecond"));
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                .body(bloodService.findAncestry(dateTime), Ancestry.class);
//    }
//
//    private Mono<ServerResponse> breedingHorseUntil(ServerRequest request) {
//        LocalDateTime dateTime = DateUtil.tolocalDateTime(request.pathVariable("epochSecond"));
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                .body(bloodService.findBreedingHorse(dateTime), BreedingHorse.class);
//    }
//
//    private Mono<ServerResponse> offspringUntil(ServerRequest request) {
//        LocalDateTime dateTime = DateUtil.tolocalDateTime(request.pathVariable("epochSecond"));
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                .body(bloodService.findOffspring(dateTime), Offspring.class);
//    }
//
//
//}
