package org.uma.cloud.stream.type;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeSinkTest {

    @Test
    void test_time() {
        long now1 = LocalDateTime.now().minusDays(5)
                .atZone(ZoneId.of("Asia/Tokyo"))
                .toInstant()
                .toEpochMilli();

        long now2 = LocalDateTime.now()
                .atZone(ZoneId.of("Asia/Tokyo"))
                .toInstant()
                .toEpochMilli();

        System.out.println(now1);
        System.out.println(now2);
    }


    @Test
    void test_time2() {
        LocalDateTime thisWeeksMonday = LocalDateTime.now().with(DayOfWeek.FRIDAY);
        System.out.println(thisWeeksMonday.atZone(ZoneId.of("Asia/Tokyo"))
                .toInstant()
                .toEpochMilli());
    }

}
