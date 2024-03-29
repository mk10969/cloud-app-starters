package org.uma.cloud.common.utils.lang;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Objects;

public class DateUtil {

    private DateUtil() {
    }

    private static final String pattern = "uuuu/MM/dd";

    private static final String timeZone = "Asia/Tokyo";


    public static String format(String format, LocalDateTime dateTime) {
        return DateTimeFormatter
                .ofPattern(format)
                .format(dateTime);
    }

    public static LocalDateTime of(String yyyyMMdd) {
        final DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern)
                .withResolverStyle(ResolverStyle.STRICT);
        // localDateをlocalDateTimeに変換する。
        return LocalDateTime.of(LocalDate.parse(yyyyMMdd, df), LocalTime.of(0, 0));
    }

    public static long now() {
        return LocalDateTime.now()
                .atZone(ZoneId.of(timeZone))
                .toInstant()
                .toEpochMilli();
    }

    public static LocalDateTime toLocalDateTime(long epochSecond) {
        // milliseconds check
        if (String.valueOf(epochSecond).length() != 13) {
            throw new IllegalArgumentException("milliseconds にしてください。");
        }
        return Instant.ofEpochMilli(epochSecond)
                .atZone(ZoneId.of(timeZone))
                .toLocalDateTime();
    }

    public static long toEpochMilli(LocalDateTime localDateTime) {
        return Objects.requireNonNull(localDateTime, "localDateTimeがnullです。")
                .atZone(ZoneId.of(timeZone))
                .toInstant()
                .toEpochMilli();
    }

    public static LocalDateTime within3years(LocalDateTime dateTime) {
        // 現在時刻より、３年以内である。
        if (LocalDateTime.now().minusYears(3L).isBefore(dateTime)) {
            // 3年以内だから、引数をそのまま返す。
            return dateTime;
        }
        throw new IllegalArgumentException("現在時刻から、３年以内のデータしか取得できません。");
    }

    public static long thisFriday() {
        LocalDateTime thisFriday = LocalDateTime.now().with(DayOfWeek.FRIDAY);
        return thisFriday.atZone(ZoneId.of(timeZone))
                .toInstant()
                .toEpochMilli();
    }

}
