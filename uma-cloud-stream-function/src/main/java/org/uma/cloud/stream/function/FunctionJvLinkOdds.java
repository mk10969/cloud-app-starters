package org.uma.cloud.stream.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.stream.service.JvLinkWebClientService;
import org.uma.cloud.stream.service.TimeSeriesService;
import reactor.core.publisher.Flux;

import java.awt.*;
import java.util.function.Function;

@Configuration
public class FunctionJvLinkOdds {

    @Autowired
    private JvLinkWebClientService jvLinkWebClientService;

    @Autowired
    private TimeSeriesService timeSeriesService;

//
//    @Bean
//    Function<Flux<String>, Flux<Point>> aaa() {
//        return raceIds -> raceIds
//                .flatMap(jvLinkWebClientService::quinella)
//                .map(i -> i.getRaceId())
//    }

}
