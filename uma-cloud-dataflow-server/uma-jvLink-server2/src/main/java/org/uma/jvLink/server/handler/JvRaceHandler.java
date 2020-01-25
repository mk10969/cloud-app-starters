//package org.uma.platform.feed.application.handler;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.ServerRequest;
//import org.springframework.web.reactive.function.server.ServerResponse;
//import org.uma.platform.common.model.HorseRacingDetails;
//import org.uma.platform.common.model.RaceRefund;
//import org.uma.platform.common.model.RacingDetails;
//import org.uma.platform.common.model.VoteCount;
//import org.uma.platform.common.utils.lang.DateUtil;
//import org.uma.platform.feed.application.service.JvRaceService;
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
//public class JvRaceHandler {
//
//    private final JvRaceService raceService;
//
//    public RouterFunction<ServerResponse> routes() {
//        return nest(path("/race"),
//                route(GET("/"), this::ping)
//                        .andRoute(GET("/racingDetails"), this::racingDetails)
//                        .andRoute(GET("/horseRacingDetails"), this::horseRacingDetails)
//                        .andRoute(GET("/raceRefund"), this::raceRefund)
//                        .andRoute(GET("/voteCount"), this::voteCount)
//                        .andRoute(GET("/racingDetails/{epochSecond}"), this::racingDetailsUntil)
//                        .andRoute(GET("/horseRacingDetails/{epochSecond}"), this::horseRacingDetailsUntil)
//                        .andRoute(GET("/raceRefund/{epochSecond}"), this::raceRefundUntil)
//                        .andRoute(GET("/voteCount/{epochSecond}"), this::voteCountUntil));
//    }
//
//
//    private Mono<ServerResponse> ping(ServerRequest request) {
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                .body(Mono.just("race endpoint"), String.class);
//    }
//
//    private Mono<ServerResponse> racingDetails(ServerRequest request) {
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                .body(raceService.findRacingDetailsOnThisWeek(), RacingDetails.class);
//    }
//
//    private Mono<ServerResponse> horseRacingDetails(ServerRequest request) {
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                .body(raceService.findHorseRacingDetailsOnThisWeek(), HorseRacingDetails.class);
//    }
//
//    private Mono<ServerResponse> raceRefund(ServerRequest request) {
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                .body(raceService.findRaceRefundOnThisWeek(), RaceRefund.class);
//    }
//
//    private Mono<ServerResponse> voteCount(ServerRequest request) {
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                .body(raceService.findVoteCountOnThisWeek(), VoteCount.class);
//    }
//
//    private Mono<ServerResponse> racingDetailsUntil(ServerRequest request) {
//        LocalDateTime dateTime = DateUtil.tolocalDateTime(request.pathVariable("epochSecond"));
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                .body(raceService.findRacingDetails(dateTime), RacingDetails.class);
//    }
//
//    private Mono<ServerResponse> horseRacingDetailsUntil(ServerRequest request) {
//        LocalDateTime dateTime = DateUtil.tolocalDateTime(request.pathVariable("epochSecond"));
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                .body(raceService.findHorseRacingDetails(dateTime), HorseRacingDetails.class);
//    }
//
//    private Mono<ServerResponse> raceRefundUntil(ServerRequest request) {
//        LocalDateTime dateTime = DateUtil.tolocalDateTime(request.pathVariable("epochSecond"));
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                .body(raceService.findRaceRefund(dateTime), RaceRefund.class);
//    }
//
//    private Mono<ServerResponse> voteCountUntil(ServerRequest request) {
//        LocalDateTime dateTime = DateUtil.tolocalDateTime(request.pathVariable("epochSecond"));
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                .body(raceService.findVoteCount(dateTime), VoteCount.class);
//    }
//
//
//}
