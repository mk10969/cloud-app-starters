package org.uma.cloud.common.code;

import com.fasterxml.jackson.annotation.JsonValue;
import org.uma.cloud.common.utils.constants.CodeEnum;

import java.util.Objects;

/**
 * 2002.曜日コード
 */
public enum WeekDayCode implements CodeEnum<Integer, WeekDayCode> {

    /**
     * デフォルト "0"の文字列から、Integer 0 にコンバートする。
     */
    DEFAULT(0, ""),
    SATURDAY(1, "土曜日"),
    SUNDAY(2, "日曜日"),
    HOLIDAY(3, "祝日"),
    MONDAY(4, "月曜日"),
    TUESDAY(5, "火曜日"),
    WEDNESDAY(6, "水曜日"),
    THURSDAY(7, "木曜日"),
    FRIDAY(8, "金曜日"),

    ;

    private final Integer code;
    private final String codeName;


    WeekDayCode(Integer code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    @JsonValue
    public String getCodeName() {
        return this.codeName;
    }


    public static WeekDayCode of(Integer code) {
        Objects.requireNonNull(code);
        if (DEFAULT.getCode().equals(code)) {
            return DEFAULT;
        }
        return CodeEnum.reversibleFindOne(code, WeekDayCode.class);
    }

}
