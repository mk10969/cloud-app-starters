package org.uma.platform.common.code;

import org.uma.platform.common.utils.constants.CodeEnum;

import java.util.Objects;

/**
 * 2011.天候コード
 */
public enum WeatherCode implements CodeEnum<Integer, WeatherCode> {

    /**
     * 未設備時のデフォルト値
     */
    DEFAULT(0, ""),
    FINE(1, "晴"),
    CLOUDY(2, "曇"),
    RAINY(3, "雨"),
    DRIZZLE(4, "小雨"),
    SNOW(5, "雪"),
    LIGHTSNOW(6, "小雪"),
    ;

    private Integer code;
    private String codeName;

    WeatherCode(Integer code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    public String getCodeName() {
        return this.codeName;
    }

    public static WeatherCode of(Integer code) {
        Objects.requireNonNull(code);
        if (DEFAULT.getCode().equals(code)) {
            return DEFAULT;
        }
        return CodeEnum.reversibleFindOne(code, WeatherCode.class);
    }
}

