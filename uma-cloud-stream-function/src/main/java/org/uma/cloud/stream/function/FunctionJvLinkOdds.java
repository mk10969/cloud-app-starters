package org.uma.cloud.stream.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.common.model.BaseModel;
import org.uma.cloud.stream.StreamFunctionProperties;
import org.uma.cloud.stream.service.JvLinkWebService;
import org.uma.cloud.stream.service.TimeSeriesService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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


    private void debug(BaseModel odds) {
        if (properties.isDebug()) {
            log.info("odds: {}", odds);
        }
    }


    @Bean
    Function<Flux<String>, Mono<Void>> raceIdToWinsPlaceBracketQuinellaToPoint() {
        return raceIds -> raceIds
                .flatMap(jvLinkWebService::winsPlaceBracketQuinella)
                .doOnNext(this::debug)
                .flatMap(timeSeriesService::oddsToPoint)
                .doOnNext(timeSeriesService::writePoint)
                .then();
    }

    @Bean
    Function<Flux<String>, Mono<Void>> raceIdToQuinellaToPoint() {
        return raceIds -> raceIds
                .flatMap(jvLinkWebService::quinella)
                .doOnNext(this::debug)
                .flatMap(timeSeriesService::oddsToPoint)
                .doOnNext(timeSeriesService::writePoint)
                .then();
    }

    @Bean
    Function<Flux<String>, Mono<Void>> raceIdToQuinellaPlaceToPoint() {
        return raceIds -> raceIds
                .flatMap(jvLinkWebService::quinellaPlace)
                .doOnNext(this::debug)
                .flatMap(timeSeriesService::oddsToPoint)
                .doOnNext(timeSeriesService::writePoint)
                .then();
    }

    @Bean
    Function<Flux<String>, Mono<Void>> raceIdToExactaToPoint() {
        return raceIds -> raceIds
                .flatMap(jvLinkWebService::exacta)
                .doOnNext(this::debug)
                .flatMap(timeSeriesService::oddsToPoint)
                .doOnNext(timeSeriesService::writePoint)
                .then();
    }

}
