//package org.uma.platform.feed.application.handler;
//
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.ServerRequest;
//import org.springframework.web.reactive.function.server.ServerResponse;
//import org.uma.platform.common.model.Course;
//import org.uma.platform.common.utils.lang.DateUtil;
//import org.uma.platform.feed.application.service.JvCommonService;
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
//public class JvCommonHandler {
//
//    private final JvCommonService commonService;
//
//
//    public RouterFunction<ServerResponse> routes() {
//        return nest(path("/common"),
//                route(GET("/"), this::ping)
//                        .andRoute(GET("/course/{epochSecond}"), this::courseUntil));
//    }
//
//    private Mono<ServerResponse> ping(ServerRequest request) {
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                .body(Mono.just("common endpoint"), String.class);
//    }
//
//    private Mono<ServerResponse> courseUntil(ServerRequest request) {
//        LocalDateTime dateTime = DateUtil.tolocalDateTime(request.pathVariable("epochSecond"));
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                .body(commonService.findCourse(dateTime), Course.class);
//    }
//
//}
