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
public class JvOddsFunction {

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
    public Function<Flux<String>, Mono<Void>> raceIdToWinsShowBracketQToPoint() {
        return raceId -> raceId
                .flatMap(jvLinkWebService::winsShowBracketQ)
                .doOnNext(this::debug)
                .flatMap(timeSeriesService::oddsToPoint)
                .doOnNext(timeSeriesService::writePoint)
                .then();
    }

    @Bean
    public Function<Flux<String>, Mono<Void>> raceIdToQuinellaToPoint() {
        return raceId -> raceId
                .flatMap(jvLinkWebService::quinella)
                .doOnNext(this::debug)
                .flatMap(timeSeriesService::oddsToPoint)
                .doOnNext(timeSeriesService::writePoint)
                .then();
    }

    @Bean
    public Function<Flux<String>, Mono<Void>> raceIdToQuinellaPlaceToPoint() {
        return raceId -> raceId
                .flatMap(jvLinkWebService::quinellaPlace)
                .doOnNext(this::debug)
                .flatMap(timeSeriesService::oddsToPoint)
                .doOnNext(timeSeriesService::writePoint)
                .then();
    }

    @Bean
    public Function<Flux<String>, Mono<Void>> raceIdToExactaToPoint() {
        return raceId -> raceId
                .flatMap(jvLinkWebService::exacta)
                .doOnNext(this::debug)
                .flatMap(timeSeriesService::oddsToPoint)
                .doOnNext(timeSeriesService::writePoint)
                .then();
    }

}
