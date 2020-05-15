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
import org.uma.cloud.stream.type.BusinessSink;
import org.uma.cloud.stream.type.JvLinkWebSource;
import org.uma.cloud.stream.type.TimeSeriesSink;
import org.uma.cloud.stream.util.BusinessMapper;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.function.Function;

@Slf4j
@Configuration
public class JvRacingFunction {

    @Autowired
    private JvLinkWebSource jvLinkWebSource;

    @Autowired
    private BusinessSink businessSink;

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
                .subscribe();
    }


    /**
     * 検索: jvLinkWebSource#realtimeRacingDetail
     * 変換: BusinessMapper#toBusinessRacing
     * 登録: businessSink#update
     * <p>
     * 新しくなったら更新する。exist check不要。
     */
    @Bean
    public Function<Flux<String>, Flux<String>> raceIdToBusinessRacing() {
        return raceId -> raceId
                .flatMap(jvLinkWebSource::realtimeRacingDetail)
                .doOnNext(this::debug)
                .map(BusinessMapper::toBusinessRacing)
                .flatMap(businessSink::update)
                .doOnNext(this::debug)
                .map(BusinessRacing::getRaceId);
    }

    /**
     * 検索: jvLinkWebSource#realtimeRacingHorseDetail
     * 変換: BusinessMapper#toBusinessRacingHorse
     * 登録: businessSink#update
     * <p>
     * 新しくなったら更新する。exist check不要。
     */
    @Bean
    public Function<Flux<String>, Flux<String>> raceIdToBusinessRacingHorse() {
        return raceId -> raceId
                .flatMap(jvLinkWebSource::realtimeRacingHorseDetail)
                .doOnNext(this::debug)
                .map(BusinessMapper::toBusinessRacingHorse)
                .flatMap(businessSink::update)
                .doOnNext(this::debug)
                .map(BusinessRacingHorse::getRaceId);
    }

    /**
     * 検索: jvLinkWebSource#eventRacingRefund
     * 変換: BusinessMapper#toBusinessRacingRefund
     * 登録: businessSink#update
     * <p>
     * 確定レース払戻
     */
    @Bean
    public Function<Flux<EventMessage>, Flux<String>> eventToRacingRefund() {
        return event -> event
                .map(EventMessage::getEventId)
                .flatMap(jvLinkWebSource::eventRacingRefund)
                .doOnNext(this::debug)
                .map(BusinessMapper::toBusinessRacingRefund)
                .flatMap(businessSink::update)
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
     * イベント通知を配信分離する。
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
     * 配信パイプラインを分離している。
     *
     * @see JvRacingFunction#JvWatchEventIdScatter()
     * <p>
     * @see JvRacingFunction#eventToRacingRefund()
     */
    @Bean
    public Function<Flux<EventMessage>, Flux<String>> eventToRacingChange() {
        return event -> event.flatMap(e -> {
            switch (e.getRecordSpec()) {
                case WH:
                    return jvLinkWebSource.eventWeight(e.getEventId())
                            .doOnNext(this::debug)
                            .flatMapMany(businessSink::update)
                            .doOnNext(this::debug)
                            .map(BusinessRacingHorse::getRaceId);
                case WE:
                    return jvLinkWebSource.eventWeather(e.getEventId())
                            .doOnNext(this::debug)
                            .flatMapMany(businessSink::update)
                            .doOnNext(this::debug)
                            .map(BusinessRacing::getRaceId);
                case JC:
                    return jvLinkWebSource.eventJockeyChange(e.getEventId())
                            .doOnNext(this::debug)
                            .flatMap(businessSink::update)
                            .doOnNext(this::debug)
                            .map(BusinessRacingHorse::getRaceId);
                case AV:
                    return jvLinkWebSource.eventAvoid(e.getEventId())
                            .doOnNext(this::debug)
                            .flatMap(businessSink::update)
                            .doOnNext(this::debug)
                            .map(BusinessRacingHorse::getRaceId);
                case TC:
                    return jvLinkWebSource.eventTimeChange(e.getEventId())
                            .doOnNext(this::debug)
                            .flatMap(businessSink::update)
                            .doOnNext(this::debug)
                            .map(BusinessRacing::getRaceId);
                case CC:
                    return jvLinkWebSource.eventCourseChange(e.getEventId())
                            .doOnNext(this::debug)
                            .flatMap(businessSink::update)
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
