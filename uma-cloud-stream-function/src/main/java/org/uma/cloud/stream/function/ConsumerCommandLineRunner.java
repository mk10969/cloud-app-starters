package org.uma.cloud.stream.function;

import org.reactivestreams.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.common.model.RacingDetails;
import org.uma.cloud.common.model.business.BusinessRace;
import org.uma.cloud.common.service.business.BusinessRaceService;
import org.uma.cloud.stream.service.JvLinkWebService;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;

@Configuration
public class ConsumerCommandLineRunner {

    @Autowired
    private JvLinkWebService jvLinkWebService;

    @Autowired
    private BusinessRaceService businessRaceService;
    
    @Autowired
    private Subscriber<BusinessRace> subscriber;

    /**
     * Steam起動時に、今週のレースIDとレース開始時刻モデルを生成する。
     * {@link org.uma.cloud.common.model.business.BusinessRace}
     */
    @Bean
    @ConditionalOnProperty(prefix = "spring.init", name = "enabled", havingValue = "true")
    public CommandLineRunner initThisWeekRace() {
        return args -> jvLinkWebService.raceDetailsThisWeek()
                .map(ConsumerCommandLineRunner::createBusinessRace)
                .doOnNext(businessRaceService::update)
                .subscribeOn(Schedulers.immediate())
                .subscribe(subscriber);
    }

    private static BusinessRace createBusinessRace(RacingDetails racingDetails) {
        BusinessRace businessRace = new BusinessRace();
        LocalDateTime raceStartDateTime = LocalDateTime.of(
                racingDetails.getHoldingDate(), racingDetails.getStartTime());
        businessRace.setRaceId(racingDetails.getRaceId());
        businessRace.setRaceStartDateTime(raceStartDateTime);
        return businessRace;
    }

}
