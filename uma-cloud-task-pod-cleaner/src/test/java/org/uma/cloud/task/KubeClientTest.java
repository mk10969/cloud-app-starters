package org.uma.cloud.task;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class KubeClientTest {

    @Test
    void test() {

    }

    /**
     * yyyy-MM-dd'T'HH:mm:ss'Z'
     * この形式の時間フォーマットは、末尾のZが大事みたい。
     * だから、LocalDatetimeではなく、OffsetDateTimeを使う！
     * https://stackoverflow.com/questions/52523216/how-to-convert-localdatetime-to-yyyy-mm-ddthhmmssz-format
     */
    public static LocalDateTime toLocalDateTime(String date) {
        String format = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(date, dtf);
    }


}
