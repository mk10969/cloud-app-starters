package org.uma.cloud.stream.function;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.common.recordSpec.RecordSpec;
import org.uma.cloud.stream.service.BusinessRxService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.UnicastProcessor;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Objects;
import java.util.function.Function;

import static org.uma.cloud.common.recordSpec.RecordSpec.HR;

@Slf4j
@Configuration
public class JvWatchEventFunction {

    @Autowired
    private BusinessRxService rxService;

    /**
     * イベント通知
     * <p>
     * EventId 例：
     * "HR:202004250310"
     * "TC:TC20200425030020200425154801"
     */
    @Bean
    public Function<Flux<String>, Tuple2<Flux<Event>, Flux<Event>>> scatterJvWatchEventId() {
        return eventId -> {
            Flux<Event> connectedFlux = eventId.map(Event::new).publish().autoConnect(2);
            UnicastProcessor<Event> isRacingRefund = UnicastProcessor.create();
            UnicastProcessor<Event> isNotRacingRefund = UnicastProcessor.create();
            Flux<Event> isRacingRefundFlux = connectedFlux.filter(Event::isRacingRefund);
            Flux<Event> isNotRacingRefundFlux = connectedFlux.filter(Event::isNotRacingRefund);
            return Tuples.of(
                    Flux.from(isRacingRefund).doOnSubscribe(e -> isRacingRefundFlux.subscribe()),
                    Flux.from(isNotRacingRefund).doOnSubscribe(e -> isNotRacingRefundFlux.subscribe())
            );
        };
    }

    /**
     * 払戻イベントだけ、ここでsubscribeしない。
     * @see BusinessFunction#updateRacingRefund()
     * <p>
     * 配信パイプラインを分離している。
     * @see JvWatchEventFunction#scatterJvWatchEventId()
     */
    @Bean
    public Function<Flux<Event>, Mono<Void>> eventSubscribe() {
        return event -> event
                .flatMap(e -> {
                    switch (e.getRecordSpec()) {
                        case WH:
                            return rxService.updateWeight(e.getRaceId());
                        case WE:
                            return rxService.updateWeather(e.getRaceId());
                        case JC:
                            return rxService.updateJockeyChange(e.getRaceId());
                        case AV:
                            return rxService.updateAvoid(e.getRaceId());
                        case TC:
                            return rxService.updateTimeChange(e.getRaceId());
                        case CC:
                            return rxService.updateCourseChange(e.getRaceId());
                        default:
                            throw new IllegalArgumentException(e + ": が正しくありません。");
                    }
                })
                .doOnError(e -> log.error("JvWatchEvent Error: ", e))
                .then();
    }


    @Getter
    public static class Event {

        private final RecordSpec recordSpec;

        private final String raceId;

        public Event(String eventId) {
            String[] tmp = eventId.split(":");
            this.recordSpec = RecordSpec.of(tmp[0]);
            this.raceId = Objects.requireNonNull(tmp[1]);
        }

        public boolean isRacingRefund() {
            return HR == this.recordSpec;
        }

        public boolean isNotRacingRefund() {
            return !this.isRacingRefund();
        }
    }

}