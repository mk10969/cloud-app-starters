package org.uma.cloud.common.code;

import com.fasterxml.jackson.annotation.JsonValue;
import org.uma.cloud.common.utils.constants.CodeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

/**
 * 2201.品種コード
 */
public enum BreedCode implements CodeEnum<Integer, BreedCode> {

    /**
     * 未設備時のデフォルト値
     */
    DEFAULT(0, ""),
    BRED1(1, "サラブレッド"),
    BRED2(2, "サラブレッド系種"),
    BRED3(3, "準サラブレッド"),
    BRED4(4, "軽半血種"),
    BRED5(5, "アングロアラブ"),
    BRED6(6, "アラブ系種"),
    BRED7(7, "アラブ"),
    BRED8(8, "中半血種"),

    ;

    private final Integer code;
    private final String codeName;

    BreedCode(Integer code, String codeName) {
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

    public static BreedCode of(Integer code) {
        Objects.requireNonNull(code);
        if (DEFAULT.getCode().equals(code)) {
            return DEFAULT;
        }
        return CodeEnum.convertByCode(code, BreedCode.class);
    }

    /**
     * Jpa enum converter impl
     */
    @Converter(autoApply = true)
    public static class converterImpl implements AttributeConverter<BreedCode, String> {
        @Override
        public String convertToDatabaseColumn(BreedCode attribute) {
            return attribute.getCodeName();
        }

        @Override
        public BreedCode convertToEntityAttribute(String dbData) {
            return CodeEnum.convertByCodeName(dbData, BreedCode.class);
        }
    }

}
