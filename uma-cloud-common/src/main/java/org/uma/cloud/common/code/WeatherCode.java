package org.uma.cloud.common.code;

import com.fasterxml.jackson.annotation.JsonValue;
import org.uma.cloud.common.utils.constants.CodeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
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

    private final Integer code;
    private final String codeName;

    WeatherCode(Integer code, String codeName) {
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

    public static WeatherCode of(Integer code) {
        Objects.requireNonNull(code);
        if (DEFAULT.getCode().equals(code)) {
            return DEFAULT;
        }
        return CodeEnum.convertByCode(code, WeatherCode.class);
    }

    /**
     * Jpa enum converter impl
     */
    @Converter(autoApply = true)
    public static class converterImpl implements AttributeConverter<WeatherCode, String> {
        @Override
        public String convertToDatabaseColumn(WeatherCode attribute) {
            return attribute.getCodeName();
        }

        @Override
        public WeatherCode convertToEntityAttribute(String dbData) {
            return CodeEnum.convertByCodeName(dbData, WeatherCode.class);
        }
    }
    
}

