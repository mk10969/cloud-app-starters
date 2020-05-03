package org.uma.cloud.common.code;

import com.fasterxml.jackson.annotation.JsonValue;
import org.uma.cloud.common.utils.constants.CodeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

/**
 * 2301.東西所属コード
 */
public enum EastOrWestBelongCode implements CodeEnum<Integer, EastOrWestBelongCode> {

    /**
     * 未設備時のデフォルト値
     */
    DEFAULT(0, ""),
    EAST(1, "美浦"),
    WEST(2, "栗東"),
    LOCAL(3, "地方招待"),
    FOREIGN(4, "外国招待"),

    ;

    private final Integer code;
    private final String codeName;

    EastOrWestBelongCode(Integer code, String codeName) {
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

    public static EastOrWestBelongCode of(Integer code) {
        Objects.requireNonNull(code);
        if (DEFAULT.getCode().equals(code)) {
            return DEFAULT;
        }
        return CodeEnum.convertByCode(code, EastOrWestBelongCode.class);
    }

    /**
     * Jpa enum converter impl
     */
    @Converter(autoApply = true)
    public static class converterImpl implements AttributeConverter<EastOrWestBelongCode, String> {
        @Override
        public String convertToDatabaseColumn(EastOrWestBelongCode attribute) {
            return attribute.getCodeName();
        }

        @Override
        public EastOrWestBelongCode convertToEntityAttribute(String dbData) {
            return CodeEnum.convertByCodeName(dbData, EastOrWestBelongCode.class);
        }
    }
}
