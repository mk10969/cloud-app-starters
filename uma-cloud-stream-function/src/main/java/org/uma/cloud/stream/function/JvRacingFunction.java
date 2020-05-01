package org.uma.cloud.stream.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.common.model.business.BusinessRacing;
import org.uma.cloud.common.model.business.BusinessRacingHorse;
import org.uma.cloud.common.model.business.BusinessRacingRefund;
import org.uma.cloud.stream.StreamFunctionProperties;
import org.uma.cloud.stream.model.Event;
import org.uma.cloud.stream.service.JvLinkWebService;
import org.uma.cloud.stream.service.ReactiveRacingService;
import org.uma.cloud.stream.service.TimeSeriesService;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Configuration
public class JvRacingFunction {

    @Autowired
    private JvLinkWebService jvLinkWebService;

    @Autowired
    private ReactiveRacingService rxService;

    @Autowired
    private TimeSeriesService timeSeriesService;

    @Autowired
    private StreamFunctionProperties properties;


    @Bean
    @ConditionalOnProperty(prefix = "uma.stream.init", name = "enabled", havingValue = "true")
    public CommandLineRunner initThisWeekRace() {
        return args -> raceIdToBusinessRacing()
                .apply(jvLinkWebService.getRaceIds())
                .subscribe(raceId -> log.info("今週のレース: {}", raceId));
    }

    /**
     * subscriber
     */
    @Bean
    public Consumer<Flux<String>> subscribe() {
        return data -> data
                .doOnNext(i -> log.info("Consumer1: {}", i))
                .subscribe();
    }

    /**
     * subscriber2
     */
    @Bean
    public Consumer<Flux<String>> subscribe2() {
        return data -> data
                .doOnNext(i -> log.info("Consumer2: {}", i))
                .subscribe();
    }


    @Bean
    public Function<Flux<String>, Flux<String>> raceIdToBusinessRacing() {
        return raceId -> raceId
                .flatMap(rxService::updateBusinessRacing)
                .doOnNext(this::debug)
                .map(BusinessRacing::getRaceId);
    }

    @Bean
    public Function<Flux<String>, Flux<String>> raceIdToBusinessRacingHorse() {
        return raceId -> raceId
                .flatMap(rxService::updateAllBusinessRacingHorse)
                .doOnNext(this::debug)
                .map(BusinessRacingHorse::getRaceId);
    }

    @Bean
    public Function<Flux<Event>, Flux<String>> eventToRacingRefund() {
        return event -> event
                .map(Event::getRaceId)
                .flatMap(rxService::updateBusinessRacingRefund)
                .doOnNext(this::debug)
                .map(BusinessRacingRefund::getRaceId);
    }


    @Bean
    public Function<Flux<String>, Flux<String>> raceIdToWinsShowBracketQ() {
        return raceId -> raceId
                .flatMap(jvLinkWebService::winsShowBracketQ)
                .doOnNext(this::debug)
                .flatMap(timeSeriesService::oddsToPoint);
    }

    @Bean
    public Function<Flux<String>, Flux<String>> raceIdToQuinella() {
        return raceId -> raceId
                .flatMap(jvLinkWebService::quinella)
                .doOnNext(this::debug)
                .flatMap(timeSeriesService::oddsToPoint);
    }

    @Bean
    public Function<Flux<String>, Flux<String>> raceIdToQuinellaPlace() {
        return raceId -> raceId
                .flatMap(jvLinkWebService::quinellaPlace)
                .doOnNext(this::debug)
                .flatMap(timeSeriesService::oddsToPoint);
    }

    @Bean
    public Function<Flux<String>, Flux<String>> raceIdToExacta() {
        return raceId -> raceId
                .flatMap(jvLinkWebService::exacta)
                .doOnNext(this::debug)
                .flatMap(timeSeriesService::oddsToPoint);
    }


    /**
     * イベント通知
     * <p>
     * EventId 例：
     * "HR:202004250310"
     * "TC:TC20200425030020200425154801"
     */
    @Bean
    public Function<Flux<String>, Tuple2<Flux<Event>, Flux<Event>>> JvWatchEventIdScatter() {
        return eventId -> {
            Flux<Event> connectedFlux = eventId.map(Event::new).publish().autoConnect(2);
            Flux<Event> isRacingRefundFlux = connectedFlux.filter(Event::isRacingRefund);
            Flux<Event> isNotRacingRefundFlux = connectedFlux.filter(Event::isNotRacingRefund);
            return Tuples.of(Flux.from(isRacingRefundFlux), Flux.from(isNotRacingRefundFlux));
        };
    }

    /**
     * 払戻イベントだけ、ここでsubscribeしない。
     * TODO: センスない
     * @see JvRacingFunction#eventToRacingRefund()
     * <p>
     * 配信パイプラインを分離している。
     * @see JvRacingFunction#JvWatchEventIdScatter()
     */
    @Bean
    public Function<Flux<Event>, Flux<String>> eventToRacingChange() {
        return event -> event
                .flatMap(e -> {
                    switch (e.getRecordSpec()) {
                        case WH:
                            return rxService.updateAllWeight(e.getRaceId())
                                    .doOnNext(this::debug)
                                    .map(BusinessRacingHorse::getRaceId);
                        case WE:
                            return rxService.updateAllWeather(e.getRaceId())
                                    .doOnNext(this::debug)
                                    .map(BusinessRacing::getRaceId);
                        case JC:
                            return rxService.updateJockeyChange(e.getRaceId())
                                    .doOnNext(this::debug)
                                    .map(BusinessRacingHorse::getRaceId);
                        case AV:
                            return rxService.updateAvoid(e.getRaceId())
                                    .doOnNext(this::debug)
                                    .map(BusinessRacingHorse::getRaceId);
                        case TC:
                            return rxService.updateTimeChange(e.getRaceId())
                                    .doOnNext(this::debug)
                                    .map(BusinessRacing::getRaceId);
                        case CC:
                            return rxService.updateCourseChange(e.getRaceId())
                                    .doOnNext(this::debug)
                                    .map(BusinessRacing::getRaceId);
                        default:
                            throw new IllegalArgumentException(e + ": が正しくありません。");
                    }
                });
    }


    private void debug(Object model) {
        if (properties.isDebug()) {
            log.info("{}", model);
        }
    }

}
