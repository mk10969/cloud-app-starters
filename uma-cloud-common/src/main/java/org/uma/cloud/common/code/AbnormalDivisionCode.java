package org.uma.cloud.common.code;

import com.fasterxml.jackson.annotation.JsonValue;
import org.uma.cloud.common.utils.constants.CodeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

/**
 * 2101.異常区分コード
 */
public enum AbnormalDivisionCode implements CodeEnum<Integer, AbnormalDivisionCode> {

    /**
     * 未設備時のデフォルト値
     */
    DEFAULT(0, "", ""),
    SC(1, "出走取消", "SCRATCHED"),
    ES(2, "発走除外", "EXCLUDED BY STARTERS"),
    ER(3, "競走除外", "EXCLUDED BY STEWARDS"),
    FF(4, "競走中止", "FALL TO FINISH"),
    DQ(5, "失格", "DISQUALIFIED"),
    RM(6, "落馬再騎乗", "REMOUNT AFTER A CROPPER"),
    DQP(7, "降着", "DISQUALIFIED AND PLACED"),

    ;

    private final Integer code;
    private final String codeName;
    private final String codeNameEng;

    AbnormalDivisionCode(Integer code, String codeName, String codeNameEng) {
        this.code = code;
        this.codeName = codeName;
        this.codeNameEng = codeNameEng;
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

    public String getCodeNameEng() {
        return this.codeNameEng;
    }

    public static AbnormalDivisionCode of(Integer code) {
        Objects.requireNonNull(code);
        if (DEFAULT.getCode().equals(code)) {
            return DEFAULT;
        }
        return CodeEnum.convertByCode(code, AbnormalDivisionCode.class);
    }

    /**
     * Jpa enum converter impl
     */
    @Converter(autoApply = true)
    public static class converterImpl implements AttributeConverter<AbnormalDivisionCode, String> {

        @Override
        public String convertToDatabaseColumn(AbnormalDivisionCode attribute) {
            return attribute.getCodeName();
        }

        @Override
        public AbnormalDivisionCode convertToEntityAttribute(String dbData) {
            return CodeEnum.convertByCodeName(dbData, AbnormalDivisionCode.class);
        }
    }

}
