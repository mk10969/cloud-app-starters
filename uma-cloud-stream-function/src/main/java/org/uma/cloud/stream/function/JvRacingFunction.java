package org.uma.cloud.stream.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.common.model.RacingDetail;
import org.uma.cloud.common.model.business.BusinessRacing;
import org.uma.cloud.common.model.business.BusinessRacingHorse;
import org.uma.cloud.common.model.business.BusinessRacingRefund;
import org.uma.cloud.common.utils.lang.DateUtil;
import org.uma.cloud.stream.StreamFunctionProperties;
import org.uma.cloud.stream.model.EventMessage;
import org.uma.cloud.stream.type.JvLinkWebSource;
import org.uma.cloud.stream.type.ReactiveRacingService;
import org.uma.cloud.stream.type.TimeSeriesSink;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Configuration
public class JvRacingFunction {

    @Autowired
    private JvLinkWebSource jvLinkWebSource;

    @Autowired
    private ReactiveRacingService rxService;

    @Autowired
    private TimeSeriesSink timeSeriesSink;

    @Autowired
    private StreamFunctionProperties properties;


    /**
     * 今週金曜日を基準日として設定して、蓄積系のracingDetailsを叩く。
     * ==> raceIdを取得する。
     */
    @Bean
    @ConditionalOnProperty(prefix = "uma.stream.init", name = "enabled", havingValue = "true")
    public CommandLineRunner initThisWeekRace() {
        return args -> raceIdToBusinessRacing()
                .andThen(raceIdToBusinessRacingHorse())
                .apply(jvLinkWebSource.storeRacingDetail(DateUtil.thisFriday())
                        .map(RacingDetail::getRaceId))
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
    public Function<Flux<EventMessage>, Flux<String>> eventToRacingRefund() {
        return event -> event
                .map(EventMessage::getEventId)
                .flatMap(rxService::updateBusinessRacingRefund)
                .doOnNext(this::debug)
                .map(BusinessRacingRefund::getRaceId);
    }


    @Bean
    public Function<Flux<String>, Flux<String>> raceIdToWinsShowBracketQ() {
        return raceId -> raceId
                .flatMap(jvLinkWebSource::realtimeWinsShowBracketQ)
                .doOnNext(this::debug)
                .flatMap(timeSeriesSink::oddsToPoint);
    }

    @Bean
    public Function<Flux<String>, Flux<String>> raceIdToQuinella() {
        return raceId -> raceId
                .flatMap(jvLinkWebSource::realtimeQuinella)
                .doOnNext(this::debug)
                .flatMap(timeSeriesSink::oddsToPoint);
    }

    @Bean
    public Function<Flux<String>, Flux<String>> raceIdToQuinellaPlace() {
        return raceId -> raceId
                .flatMap(jvLinkWebSource::realtimeQuinellaPlace)
                .doOnNext(this::debug)
                .flatMap(timeSeriesSink::oddsToPoint);
    }

    @Bean
    public Function<Flux<String>, Flux<String>> raceIdToExacta() {
        return raceId -> raceId
                .flatMap(jvLinkWebSource::realtimeExacta)
                .doOnNext(this::debug)
                .flatMap(timeSeriesSink::oddsToPoint);
    }


    /**
     * イベント通知
     * <p>
     * EventId 例：
     * "HR:202004250310"
     * "TC:TC20200425030020200425154801"
     */
    @Bean
    public Function<Flux<EventMessage>, Tuple2<Flux<EventMessage>, Flux<EventMessage>>> JvWatchEventIdScatter() {
        return eventId -> {
            Flux<EventMessage> connectedFlux = eventId.publish().autoConnect(2);
            Flux<EventMessage> isRacingRefundFlux = connectedFlux.filter(EventMessage::isRacingRefund);
            Flux<EventMessage> isNotRacingRefundFlux = connectedFlux.filter(EventMessage::isNotRacingRefund);
            return Tuples.of(Flux.from(isRacingRefundFlux), Flux.from(isNotRacingRefundFlux));
        };
    }

    /**
     * 払戻イベントだけ、ここでsubscribeしない。
     * TODO: センスない
     *
     * @see JvRacingFunction#eventToRacingRefund()
     * <p>
     * 配信パイプラインを分離している。
     * @see JvRacingFunction#JvWatchEventIdScatter()
     */
    @Bean
    public Function<Flux<EventMessage>, Flux<String>> eventToRacingChange() {
        return event -> event
                .flatMap(e -> {
                    switch (e.getRecordSpec()) {
                        case WH:
                            return rxService.updateAllWeight(e.getEventId())
                                    .doOnNext(this::debug)
                                    .map(BusinessRacingHorse::getRaceId);
                        case WE:
                            return rxService.updateAllWeather(e.getEventId())
                                    .doOnNext(this::debug)
                                    .map(BusinessRacing::getRaceId);
                        case JC:
                            return rxService.updateJockeyChange(e.getEventId())
                                    .doOnNext(this::debug)
                                    .map(BusinessRacingHorse::getRaceId);
                        case AV:
                            return rxService.updateAvoid(e.getEventId())
                                    .doOnNext(this::debug)
                                    .map(BusinessRacingHorse::getRaceId);
                        case TC:
                            return rxService.updateTimeChange(e.getEventId())
                                    .doOnNext(this::debug)
                                    .map(BusinessRacing::getRaceId);
                        case CC:
                            return rxService.updateCourseChange(e.getEventId())
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
