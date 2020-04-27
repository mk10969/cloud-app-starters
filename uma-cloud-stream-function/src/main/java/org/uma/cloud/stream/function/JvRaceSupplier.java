package org.uma.cloud.stream.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.context.PollableBean;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.common.model.business.BusinessRacing;
import org.uma.cloud.common.service.business.BusinessRacingService;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.function.Supplier;

@Configuration
public class JvRaceSupplier {

    @Autowired
    private BusinessRacingService racingService;

    /**
     * fixedDelay          ->  Default: 1000L.
     * maxMessagesPerPoll  ->  Default: 1L.
     */
    @PollableBean
    public Supplier<Flux<String>> comingRaceIds() {
        return () -> Flux.defer(() -> Flux.fromIterable(racingService.findComingRaces()))
                .map(BusinessRacing::getRaceId)
                .sort()
                .delayElements(Duration.ofMillis(200L)) // 気持ちdelayかませる。
                .log();
    }

}
