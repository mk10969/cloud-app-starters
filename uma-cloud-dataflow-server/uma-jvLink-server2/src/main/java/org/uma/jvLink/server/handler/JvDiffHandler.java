//package org.uma.platform.feed.application.handler;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.ServerRequest;
//import org.springframework.web.reactive.function.server.ServerResponse;
//import org.uma.platform.common.model.*;
//import org.uma.platform.common.utils.lang.DateUtil;
//import org.uma.platform.feed.application.service.JvDiffService;
//import reactor.core.publisher.Mono;
//
//import java.time.LocalDateTime;
//
//import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
//import static org.springframework.web.reactive.function.server.RequestPredicates.path;
//import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
//import static org.springframework.web.reactive.function.server.RouterFunctions.route;
//
//
//@Component
//@RequiredArgsConstructor
//public class JvDiffHandler {
//
//    private final JvDiffService diffService;
//
//    public RouterFunction<ServerResponse> routes() {
//        return nest(path("/diff"),
//                route(GET("/"), this::ping)
//                        .andRoute(GET("/raceHorse/{epochSecond}"), this::raceHorseUntil)
//                        .andRoute(GET("/jockey/{epochSecond}"), this::jockeyUntil)
//                        .andRoute(GET("/trainer/{epochSecond}"), this::trainerUntil)
//                        .andRoute(GET("/owner/{epochSecond}"), this::ownerUntil)
//                        .andRoute(GET("/breeder/{epochSecond}"), this::breederUntil));
//    }
//
//    private Mono<ServerResponse> ping(ServerRequest request) {
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                .body(Mono.just("diff endpoint"), String.class);
//    }
//
//    private Mono<ServerResponse> raceHorseUntil(ServerRequest request) {
//        LocalDateTime dateTime = DateUtil.tolocalDateTime(request.pathVariable("epochSecond"));
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                .body(diffService.findRaceHorse(dateTime), RaceHorse.class);
//    }
//
//    private Mono<ServerResponse> jockeyUntil(ServerRequest request) {
//        LocalDateTime dateTime = DateUtil.tolocalDateTime(request.pathVariable("epochSecond"));
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                .body(diffService.findJockey(dateTime), Jockey.class);
//    }
//
//    private Mono<ServerResponse> trainerUntil(ServerRequest request) {
//        LocalDateTime dateTime = DateUtil.tolocalDateTime(request.pathVariable("epochSecond"));
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                .body(diffService.findTrainer(dateTime), Trainer.class);
//    }
//
//    private Mono<ServerResponse> ownerUntil(ServerRequest request) {
//        LocalDateTime dateTime = DateUtil.tolocalDateTime(request.pathVariable("epochSecond"));
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                .body(diffService.findOwner(dateTime), Owner.class);
//    }
//
//    private Mono<ServerResponse> breederUntil(ServerRequest request) {
//        LocalDateTime dateTime = DateUtil.tolocalDateTime(request.pathVariable("epochSecond"));
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                .body(diffService.findBreeder(dateTime), Breeder.class);
//    }
//
//
//}
