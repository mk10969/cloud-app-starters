package org.uma.platform.common.utils.lang;

import org.junit.jupiter.api.Test;
import org.uma.platform.common.utils.lang.DateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateUtilTest {


    @Test
    void test() {
        LocalDateTime dateTime = LocalDateTime.now();
        String localDatetime = DateUtil.format("yyyyMMddHHmmss", dateTime);


        String zonedDateTime = DateTimeFormatter
                .ofPattern("yyyyMMddHHmmss")
                .format(ZonedDateTime.now());

        assertEquals(localDatetime, zonedDateTime);

    }

    @Test
    void test_日付比較() {
        LocalDateTime dateTime = LocalDateTime.of(2017, 1, 1, 1, 1, 1);

        // 今から３年より昔であるかどうか。
        // ３年より昔ではない！
        assertTrue(LocalDateTime.now().minusYears(3L).isBefore(dateTime));

    }


    @Test
    void test_日付のTOString(){
        LocalDate dateTime = LocalDate.now();
        System.out.println(dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    }

    @Test
    void test_一番古い日付(){
       LocalDate localDate = LocalDate.MIN;
       System.out.println(localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    }


    @Test
    void test_パーサーテスト(){
        String str = "2019/01/01";
        LocalDateTime datetime = DateUtil.of(str);
        String expect = DateUtil.format("yyyyMMddHHmmss", datetime);
        assertEquals(expect, "20190101000000");

    }


}