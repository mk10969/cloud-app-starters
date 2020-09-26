package org.uma.cloud.stream.type;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.uma.cloud.common.entity.WeekendRacingDetail;
import org.uma.cloud.common.service.WeekendRacingHorseDetailService;
import org.uma.cloud.common.service.WeekendRacingDetailService;
import org.uma.cloud.common.utils.lang.DateUtil;
import org.uma.cloud.stream.util.BusinessMapper;

@SpringBootTest
public class BusinessXXXXXServiceTest {

    @Autowired
    private JvLinkWebSource jvLinkWebSource;

    @Autowired
    private WeekendRacingDetailService racingService;

    @Autowired
    private WeekendRacingHorseDetailService racingHorseService;


    @Test
    void test_findFinshedRacesがちゃんととれるか確認() {
        racingService.findFinishedRaces().forEach(System.out::println);
    }

    @Test
    void test_RaceIdをとってSEのデータをとってDB書き込み() {
        jvLinkWebSource.storeRacingDetail(DateUtil.thisFriday())
                .map(BusinessMapper::toBusinessRacing)
                .doOnNext(racingService::update)
                .map(WeekendRacingDetail::getRaceId)
                .flatMap(jvLinkWebSource::realtimeRacingHorseDetail)
                .map(BusinessMapper::toBusinessRacingHorse)
                .doOnNext(racingHorseService::update)
                .toStream()
                .forEach(System.out::println);
    }
}
