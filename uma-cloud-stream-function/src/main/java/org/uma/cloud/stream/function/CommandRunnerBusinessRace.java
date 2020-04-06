package org.uma.cloud.stream.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.common.model.RacingDetails;
import org.uma.cloud.common.model.business.BusinessRace;
import org.uma.cloud.common.service.business.BusinessRaceService;
import org.uma.cloud.stream.service.JvLinkWebClientService;

import java.time.LocalDateTime;

@Configuration
public class CommandRunnerBusinessRace {

    @Autowired
    private JvLinkWebClientService jvLinkWebClientService;

    @Autowired
    private BusinessRaceService businessRaceService;

    /**
     * Steam起動時に、今週のレースIDとレース開始時刻モデルを生成する。
     * {@link org.uma.cloud.common.model.business.BusinessRace}
     */
    @Bean
    public CommandLineRunner initThisWeekRace() {
        return args -> jvLinkWebClientService.raceDetailsThisWeek()
                .map(this::createBusinessRace)
                .toStream()
                .forEach(businessRaceService::update);

    }

    private BusinessRace createBusinessRace(RacingDetails racingDetails) {
        BusinessRace businessRace = new BusinessRace();
        LocalDateTime raceStartDateTime = LocalDateTime.of(racingDetails.getHoldingDate(), racingDetails.getStartTime());
        businessRace.setRaceId(racingDetails.getRaceId());
        businessRace.setRaceStartDateTime(raceStartDateTime);
        return businessRace;
    }

}
