package org.uma.cloud.stream.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.common.entity.RacingDetail;
import org.uma.cloud.common.entity.WeekendRacingDetail;
import org.uma.cloud.common.entity.WeekendRacingHorseDetail;
import org.uma.cloud.common.entity.WeekendRacingRefund;
import org.uma.cloud.common.utils.lang.DateUtil;
import org.uma.cloud.stream.StreamFunctionProperties;
import org.uma.cloud.stream.model.EventMessage;
import org.uma.cloud.stream.type.JvLinkWebSource;
import org.uma.cloud.stream.type.TimeSeriesSink;
import org.uma.cloud.stream.type.WeekendRacingSink;
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
    private WeekendRacingSink weekendRacingSink;

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
        return args -> raceIdToWeekendRacingDetail()
                .andThen(raceIdToWeekendRacingHorseDetail())
                .apply(jvLinkWebSource.storeRacingDetail(DateUtil.thisFriday())
                        .map(RacingDetail::getRaceId))
                .subscribe();
    }

    /**
     * 今週開催レースを再度取得する。
     * 土曜開催が終わった後に再度データを取得したいときに利用。
     */
    @Bean
    @ConditionalOnProperty(prefix = "uma.stream.rerun", name = "enabled", havingValue = "true")
    public CommandLineRunner reRun() {
        // TODO: DBから　RaceId取ろう。
        return args ->
                raceIdToWeekendRacingRefund()
//                        .andThen(raceIdToWeekendRacingHorseDetail())
//                        .andThen(raceIdToWeekendRacingRefund())
                        .apply(jvLinkWebSource.storeRacingDetailOnThisWeek().map(RacingDetail::getRaceId))
                        .subscribe();
    }


    /**
     * 検索: jvLinkWebSource#realtimeRacingDetail
     * 登録: weekendRacingSink#update
     */
    @Bean
    public Function<Flux<String>, Flux<String>> raceIdToWeekendRacingDetail() {
        return raceId -> raceId
                .flatMap(jvLinkWebSource::realtimeRacingDetail)
                .flatMap(weekendRacingSink::update)
                .doOnNext(this::debug)
                .map(WeekendRacingDetail::getRaceId);
    }

    /**
     * 検索: jvLinkWebSource#realtimeRacingHorseDetail
     * 登録: weekendRacingSink#update
     */
    @Bean
    public Function<Flux<String>, Flux<String>> raceIdToWeekendRacingHorseDetail() {
        return raceId -> raceId
                .flatMap(jvLinkWebSource::realtimeRacingHorseDetail)
                .flatMap(weekendRacingSink::update)
                .doOnNext(this::debug)
                .map(WeekendRacingHorseDetail::getRaceId);
    }

    /**
     * 検索: jvLinkWebSource#eventRacingRefund
     * 登録: weekendRacingSink#update
     */
    @Bean
    public Function<Flux<String>, Flux<String>> raceIdToWeekendRacingRefund() {
        return raceId -> raceId
                .flatMap(jvLinkWebSource::eventRacingRefund)
                .flatMap(weekendRacingSink::update)
                .doOnNext(this::debug)
                .map(WeekendRacingRefund::getRaceId);
    }

    /**
     * 確定レース払戻 ->
     * TODO: betRankがnullになる。。
     */
    @Bean
    public Function<Flux<EventMessage>, Flux<String>> eventToWeekendRacingRefund() {
        return event -> event
                .map(EventMessage::getEventId)
                .flatMap(jvLinkWebSource::eventRacingRefund)
                .flatMap(weekendRacingSink::update)
                .doOnNext(this::debug)
                .map(WeekendRacingRefund::getRaceId);
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
     * @see JvRacingFunction#eventToWeekendRacingRefund()
     */
    @Bean
    public Function<Flux<EventMessage>, Flux<String>> eventToRacingChange() {
        return event -> event.flatMap(e -> {
            switch (e.getRecordSpec()) {
                case WH:
                    return jvLinkWebSource.eventWeight(e.getEventId())
                            .doOnNext(this::debug)
                            .flatMapMany(weekendRacingSink::update)
                            .doOnNext(this::debug)
                            .map(WeekendRacingHorseDetail::getRaceId);
                case WE:
                    return jvLinkWebSource.eventWeather(e.getEventId())
                            .doOnNext(this::debug)
                            .flatMapMany(weekendRacingSink::update)
                            .doOnNext(this::debug)
                            .map(WeekendRacingDetail::getRaceId);
                case JC:
                    return jvLinkWebSource.eventJockeyChange(e.getEventId())
                            .doOnNext(this::debug)
                            .flatMap(weekendRacingSink::update)
                            .doOnNext(this::debug)
                            .map(WeekendRacingHorseDetail::getRaceId);
                case AV:
                    return jvLinkWebSource.eventAvoid(e.getEventId())
                            .doOnNext(this::debug)
                            .flatMap(weekendRacingSink::update)
                            .doOnNext(this::debug)
                            .map(WeekendRacingHorseDetail::getRaceId);
                case TC:
                    return jvLinkWebSource.eventTimeChange(e.getEventId())
                            .doOnNext(this::debug)
                            .flatMap(weekendRacingSink::update)
                            .doOnNext(this::debug)
                            .map(WeekendRacingDetail::getRaceId);
                case CC:
                    return jvLinkWebSource.eventCourseChange(e.getEventId())
                            .doOnNext(this::debug)
                            .flatMap(weekendRacingSink::update)
                            .doOnNext(this::debug)
                            .map(WeekendRacingDetail::getRaceId);
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
