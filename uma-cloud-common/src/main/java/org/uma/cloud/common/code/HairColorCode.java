package org.uma.cloud.common.code;

import com.fasterxml.jackson.annotation.JsonValue;
import org.uma.cloud.common.utils.constants.CodeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

/**
 * 2203.毛色コード
 */
public enum HairColorCode implements CodeEnum<String, HairColorCode> {

    /**
     * 未設備時のデフォルト値
     */
    DEFAULT("00", ""),
    KURIGE("01", "栗毛"),
    TOCHIKURIGE("02", "栃栗毛"),
    KAGE("03", "鹿毛"),
    KUROKAGE("04", "黒鹿毛"),
    AOKAGE("05", "青鹿毛"),
    AOGE("06", "青毛"),
    ASHIGE("07", "芦毛"),
    KURIKASUGE("08", "栗粕毛"),
    SHIKAKASUGE("09", "鹿粕毛"),
    AOKASUGE("10", "青粕毛"),
    SHIROGE("11", "白毛"),
    ;

    private final String code;
    private final String codeName;

    HairColorCode(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    @JsonValue
    public String getCodeName() {
        return this.codeName;
    }

    public static HairColorCode of(String code) {
        Objects.requireNonNull(code);
        if (DEFAULT.getCode().equals(code)) {
            return DEFAULT;
        }
        return CodeEnum.convertByCode(code, HairColorCode.class);
    }

    /**
     * Jpa enum converter impl
     */
    @Converter(autoApply = true)
    public static class converterImpl implements AttributeConverter<HairColorCode, String> {
        @Override
        public String convertToDatabaseColumn(HairColorCode attribute) {
            return attribute.getCodeName();
        }

        @Override
        public HairColorCode convertToEntityAttribute(String dbData) {
            return CodeEnum.convertByCodeName(dbData, HairColorCode.class);
        }
    }
}
