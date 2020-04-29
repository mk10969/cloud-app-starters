package org.uma.cloud.stream.function;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.context.PollableBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.common.model.business.BusinessRacing;
import org.uma.cloud.common.service.business.BusinessRacingService;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;

import java.time.Duration;
import java.util.function.Supplier;

@Configuration
public class JvRaceSupplier {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private BusinessRacingService racingService;

    @Getter
    private final EmitterProcessor<String> processor = EmitterProcessor.create();


    @Bean
    public Supplier<Flux<String>> dynamicRaceId() {
        return () -> processor.log();
    }


    /**
     * fixedDelay          ->  Default: 1000L.
     * maxMessagesPerPoll  ->  Default: 1L.
     */
    @PollableBean
    public Supplier<Flux<String>> pollerRaceId() {
        return () -> Flux.defer(() -> Flux.fromIterable(racingService.findComingRaces()))
                .publishOn(scheduler)
                .map(BusinessRacing::getRaceId)
                .sort()
                .delayElements(Duration.ofMillis(200L)) // 気持ちdelayかませる。
                .log();
    }

}
