package org.uma.cloud.stream.function;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.uma.cloud.common.service.business.BusinessRaceService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

@SpringBootTest
class SupplierJvLinkRaceIdTest {


    @Autowired
    private BusinessRaceService businessRaceService;


    private static final Supplier<Long> now = System::currentTimeMillis;


    @Test
    void test_getComingRaceId() {
        long a = businessRaceService.findComingRaces().stream().count();
        System.out.println(a);
    }


    @Test
    void test() {
        long now11 = Instant.now().toEpochMilli();
        System.out.println(now.get());
        System.out.println(now11);

        System.out.println(toLocalDateTime(now.get()));
        System.out.println(toLocalDateTime(now11));

        System.out.println(format("yyyyMMddHHmmss", toLocalDateTime(now.get())));

    }

    private static final String timeZone = "Asia/Tokyo";

    public static LocalDateTime toLocalDateTime(long epochSecond) {
        return Instant.ofEpochMilli(epochSecond)
                .atZone(ZoneId.of(timeZone))
                .toLocalDateTime();
    }

    public static String format(String format, LocalDateTime dateTime) {
        return DateTimeFormatter
                .ofPattern(format)
                .format(dateTime);
    }


}