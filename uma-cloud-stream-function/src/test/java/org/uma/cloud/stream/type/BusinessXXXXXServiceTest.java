package org.uma.cloud.stream.type;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.uma.cloud.common.business.BusinessRacing;
import org.uma.cloud.common.service.business.BusinessRacingHorseService;
import org.uma.cloud.common.service.business.BusinessRacingService;
import org.uma.cloud.common.utils.lang.DateUtil;
import org.uma.cloud.stream.util.BusinessMapper;

@SpringBootTest
public class BusinessXXXXXServiceTest {

    @Autowired
    private JvLinkWebSource jvLinkWebSource;

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
        jvLinkWebSource.storeRacingDetail(DateUtil.thisFriday())
                .map(BusinessMapper::toBusinessRacing)
                .doOnNext(racingService::update)
                .map(BusinessRacing::getRaceId)
                .flatMap(jvLinkWebSource::realtimeRacingHorseDetail)
                .map(BusinessMapper::toBusinessRacingHorse)
                .doOnNext(racingHorseService::update)
                .toStream()
                .forEach(System.out::println);
    }
}
