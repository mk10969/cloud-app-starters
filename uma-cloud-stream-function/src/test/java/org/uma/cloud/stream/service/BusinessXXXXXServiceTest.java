package org.uma.cloud.stream.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.uma.cloud.common.model.business.BusinessRacing;
import org.uma.cloud.common.service.business.BusinessRacingHorseService;
import org.uma.cloud.common.service.business.BusinessRacingService;
import org.uma.cloud.stream.util.BusinessMapper;

@SpringBootTest
public class BusinessXXXXXServiceTest {

    @Autowired
    private JvLinkWebService jvLinkWebService;

    @Autowired
    private BusinessRacingService racingService;

    @Autowired
    private BusinessRacingHorseService racingHorseService;


    @Test
    void test_findFinshedRacesがちゃんととれるか確認() {
        racingService.findFinishedRaces().forEach(System.out::println);
    }

    @Test
    void test_RaceIdをとってSEのデータをとってDB書き込み() {
        jvLinkWebService.raceDetailWithFriday()
                .map(BusinessMapper::toBusinessRacing)
                .doOnNext(racingService::update)
                .map(BusinessRacing::getRaceId)
                .flatMap(jvLinkWebService::racingHorseDetail)
                .map(BusinessMapper::toBusinessRacingHorse)
                .doOnNext(racingHorseService::update)
                .toStream()
                .forEach(System.out::println);
    }
}
