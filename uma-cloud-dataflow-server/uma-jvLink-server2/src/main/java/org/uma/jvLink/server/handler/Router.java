//package org.uma.platform.feed.application.handler;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.ServerResponse;
//import org.uma.platform.feed.application.handler.JvBloodHandler;
//import org.uma.platform.feed.application.handler.JvCommonHandler;
//import org.uma.platform.feed.application.handler.JvDiffHandler;
//import org.uma.platform.feed.application.handler.JvRaceHandler;
//
//import static org.springframework.web.reactive.function.server.RequestPredicates.path;
//import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
//
//@Configuration
//public class Router {
//
//    @Bean
//    public RouterFunction<ServerResponse> allRoutes(
//            JvRaceHandler raceHandler,
//            JvDiffHandler diffHandler,
//            JvBloodHandler bloodHandler,
//            JvCommonHandler commonHandler) {
//        return nest(path("/api"),
//                raceHandler.routes()
//                        .and(diffHandler.routes())
//                        .and(bloodHandler.routes())
//                        .and(commonHandler.routes())
//        );
//    }
//
//
//}
