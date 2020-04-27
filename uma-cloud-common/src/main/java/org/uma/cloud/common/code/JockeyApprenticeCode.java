package org.uma.cloud.common.code;

import com.fasterxml.jackson.annotation.JsonValue;
import org.uma.cloud.common.utils.constants.CodeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

/**
 * 2303.騎手見習コード
 */
public enum JockeyApprenticeCode implements CodeEnum<Integer, JockeyApprenticeCode> {

    /**
     * 未設備時のデフォルト値
     */
    DEFAULT(0, "", ""),
    _1Kg(1, "1Kg減", "☆"),
    _2Kg(2, "2Kg減", "△"),
    _3Kg(3, "3Kg減", "▲"),

    /**
     * 女性騎手専用コード
     */
    _4Kg_F(4, "4Kg減", "★"),
    _2Kg_F(9, "2Kg減", "◇");


    private final Integer code;
    private final String codeName;
    private final String codeMark;

    JockeyApprenticeCode(Integer code, String codeName, String codeMark) {
        this.code = code;
        this.codeName = codeName;
        this.codeMark = codeMark;
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

    public String getCodeMark() {
        return this.codeMark;
    }

    public static JockeyApprenticeCode of(Integer code) {
        Objects.requireNonNull(code);
        if (DEFAULT.getCode().equals(code)) {
            return DEFAULT;
        }
        return CodeEnum.reversibleFindOne(code, JockeyApprenticeCode.class);
    }

    /**
     * Jpa enum converter impl
     */
    @Converter(autoApply = true)
    public static class converterImpl implements AttributeConverter<JockeyApprenticeCode, String> {
        @Override
        public String convertToDatabaseColumn(JockeyApprenticeCode attribute) {
            return attribute.getCodeName();
        }

        @Override
        public JockeyApprenticeCode convertToEntityAttribute(String dbData) {
            return CodeEnum.convertOf(dbData, JockeyApprenticeCode.class);
        }
    }

}
