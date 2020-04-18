package org.uma.cloud.stream.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.context.PollableBean;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.common.model.business.BusinessRace;
import org.uma.cloud.common.service.business.BusinessRaceService;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.function.Supplier;

@Configuration
public class SupplierJvLinkRaceId {

    @Autowired
    private BusinessRaceService businessRaceService;

    /**
     * fixedDelay          ->  Default: 1000L.
     * maxMessagesPerPoll  ->  Default: 1L.
     */
    @PollableBean
    public Supplier<Flux<String>> comingRaceIds() {
        return () -> Flux.defer(() -> Flux.fromIterable(businessRaceService.findComingRaces()))
                .map(BusinessRace::getRaceId)
                .sort()
                .delayElements(Duration.ofMillis(200L)) // 気持ちdelayかませる。
                .log();
    }

}
