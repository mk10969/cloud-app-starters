package org.uma.cloud.common.code;

import com.fasterxml.jackson.annotation.JsonValue;
import org.uma.cloud.common.utils.constants.CodeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

/**
 * 2202.性別コード
 */
public enum SexCode implements CodeEnum<Integer, SexCode> {

    /**
     * 未設備時のデフォルト値
     */
    DEFAULT(0, ""),
    STALLION(1, "牡馬"),
    MARE(2, "牝馬"),
    GELDING(3, "騸馬"),
    ;

    private final Integer code;
    private final String codeName;

    SexCode(Integer code, String codeName) {
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

    public static SexCode of(Integer code) {
        Objects.requireNonNull(code);
        if (DEFAULT.getCode().equals(code)) {
            return DEFAULT;
        }
        return CodeEnum.convertByCode(code, SexCode.class);
    }

    /**
     * Jpa enum converter impl
     */
    @Converter(autoApply = true)
    public static class converterImpl implements AttributeConverter<SexCode, String> {
        @Override
        public String convertToDatabaseColumn(SexCode attribute) {
            return attribute.getCodeName();
        }

        @Override
        public SexCode convertToEntityAttribute(String dbData) {
            return CodeEnum.convertByCodeName(dbData, SexCode.class);
        }
    }

}
