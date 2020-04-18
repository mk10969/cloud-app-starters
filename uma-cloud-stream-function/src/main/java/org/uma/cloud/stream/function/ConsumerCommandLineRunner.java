package org.uma.cloud.stream.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.common.model.RacingDetails;
import org.uma.cloud.common.model.business.BusinessRace;
import org.uma.cloud.common.service.business.BusinessRaceService;
import org.uma.cloud.stream.service.JvLinkWebService;

import java.time.LocalDateTime;

@Slf4j
@Configuration
public class ConsumerCommandLineRunner {

    @Autowired
    private JvLinkWebService jvLinkWebService;

    @Autowired
    private BusinessRaceService businessRaceService;


    /**
     * TODO: これやっぱバッチの方がいいわ
     * Steam起動時に、今週のレースIDとレース開始時刻モデルを生成する。
     * {@link org.uma.cloud.common.model.business.BusinessRace}
     */
    @Bean
    @ConditionalOnProperty(prefix = "spring.init", name = "enabled", havingValue = "true")
    public CommandLineRunner initThisWeekRace() {
        return args -> jvLinkWebService.raceDetailsWithFriday()
                .map(ConsumerCommandLineRunner::createBusinessRace)
                .doOnNext(businessRaceService::update) //新しくなったら更新する。exist check不要
                .toStream()
                .forEach(i -> log.info("今週のレース開催: {}", i));
    }

    private static BusinessRace createBusinessRace(RacingDetails racingDetails) {
        BusinessRace businessRace = new BusinessRace();
        businessRace.setRaceId(racingDetails.getRaceId());
        businessRace.setRaceStartDateTime(LocalDateTime.of(
                racingDetails.getHoldingDate(), racingDetails.getStartTime()));
        businessRace.setDataDiv(racingDetails.getDataDiv());
        businessRace.setCourseCd(racingDetails.getCourseCd());
        businessRace.setRaceNameFull(racingDetails.getRaceNameFull());

        return businessRace;
    }

}
