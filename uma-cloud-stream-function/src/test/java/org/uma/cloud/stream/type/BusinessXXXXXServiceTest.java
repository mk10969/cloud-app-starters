package org.uma.cloud.stream.type;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.uma.cloud.common.service.WeekendRacingDetailService;
import org.uma.cloud.common.service.WeekendRacingHorseDetailService;

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
    
}
