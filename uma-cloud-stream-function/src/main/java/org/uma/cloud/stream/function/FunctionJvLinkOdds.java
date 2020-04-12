package org.uma.cloud.stream.function;

import lombok.extern.slf4j.Slf4j;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.stream.StreamFunctionProperties;
import org.uma.cloud.stream.service.JvLinkWebService;
import org.uma.cloud.stream.service.TimeSeriesService;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Slf4j
@Configuration
public class FunctionJvLinkOdds {

    @Autowired
    private JvLinkWebService jvLinkWebService;

    @Autowired
    private TimeSeriesService timeSeriesService;

    @Autowired
    private StreamFunctionProperties properties;

    @Bean
    Function<Flux<String>, Flux<Point>> raceIdsToQuinellaPoint() {
        return raceIds -> raceIds
                .flatMap(jvLinkWebService::quinella)
                .doOnNext(odds -> {
                    if (properties.isDebug()) {
                        log.info("odds: {}", odds);
                    }
                })
                .flatMap(timeSeriesService::toPointQuinella);
    }

}
