package org.uma.platform.common.code;

import org.uma.platform.common.utils.constants.CodeEnum;

import java.util.Objects;

/**
 * 2002.曜日コード
 */
public enum WeekDayCode implements CodeEnum<Integer, WeekDayCode> {

    /**
     * デフォルト "0"の文字列から、Integer 0 にコンバートする。
     */
    DEFAULT(0, "", ""),
    SATURDAY(1, "土曜日", "土"),
    SUNDAY(2, "日曜日", "日"),
    HOLIDAY(3, "祝日", "祝"),
    MONDAY(4, "月曜日", "月"),
    TUESDAY(5, "火曜日", "火"),
    WEDNESDAY(6, "水曜日", "水"),
    THURSDAY(7, "木曜日", "木"),
    FRIDAY(8, "金曜日", "金"),

    ;

    private Integer code;
    private String weekDay;
    private String weekDayShort;

    WeekDayCode(Integer code, String weekDay, String weekDayShort) {
        this.code = code;
        this.weekDay = weekDay;
        this.weekDayShort = weekDayShort;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    public String getWeekDay() {
        return this.weekDay;
    }

    public String getWeekDayShort() {
        return this.weekDayShort;
    }

    public static WeekDayCode of(Integer code) {
        Objects.requireNonNull(code);
        if (DEFAULT.getCode().equals(code)) {
            return DEFAULT;
        }
        return CodeEnum.reversibleFindOne(code, WeekDayCode.class);
    }


}
