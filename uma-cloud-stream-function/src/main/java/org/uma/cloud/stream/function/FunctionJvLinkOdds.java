package org.uma.cloud.stream.function;

import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.stream.service.JvLinkWebService;
import org.uma.cloud.stream.service.TimeSeriesService;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Configuration
public class FunctionJvLinkOdds {

    @Autowired
    private JvLinkWebService jvLinkWebService;

    @Autowired
    private TimeSeriesService timeSeriesService;


    @Bean
    Function<Flux<String>, Flux<Point>> raceIdsToQuinellaPoint() {
        return raceIds -> raceIds
                .flatMap(jvLinkWebService::quinella)
                .flatMap(timeSeriesService::toPointQuinella);
    }

}
