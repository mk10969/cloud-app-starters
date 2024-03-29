package org.uma.cloud.common.code;

import com.fasterxml.jackson.annotation.JsonValue;
import org.uma.cloud.common.utils.constants.CodeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

/**
 * 2010.馬場状態コード
 */
public enum TurfOrDirtConditionCode implements CodeEnum<Integer, TurfOrDirtConditionCode> {

    /**
     * デフォルト
     */
    DEFAULT(0, ""),
    FmSt(1, "良"),
    GdGd(2, "稍重"),
    YlMy(3, "重"),
    SfSy(4, "不良"),

    /**
     * 芝
     * Firm
     * Good
     * Yielding
     * Soft
     */

    /**
     * ダート
     * Standard
     * Good
     * Muddy
     * Sloppy
     */
    ;

    private final Integer code;
    private final String codeName;

    TurfOrDirtConditionCode(Integer code, String codeName) {
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

    public static TurfOrDirtConditionCode of(Integer code) {
        Objects.requireNonNull(code);
        if (DEFAULT.getCode().equals(code)) {
            return DEFAULT;
        }
        return CodeEnum.convertByCode(code, TurfOrDirtConditionCode.class);
    }

    public static TurfOrDirtConditionCode compare(
            TurfOrDirtConditionCode left,
            TurfOrDirtConditionCode right
    ) {
        if (left == TurfOrDirtConditionCode.DEFAULT
                && right != TurfOrDirtConditionCode.DEFAULT) {
            return right;
        } else if (left != TurfOrDirtConditionCode.DEFAULT
                && right == TurfOrDirtConditionCode.DEFAULT) {
            return left;
        } else {
            return TurfOrDirtConditionCode.DEFAULT;
        }
    }

    /**
     * Jpa enum converter impl
     */
    @Converter(autoApply = true)
    public static class converterImpl implements AttributeConverter<TurfOrDirtConditionCode, String> {
        @Override
        public String convertToDatabaseColumn(TurfOrDirtConditionCode attribute) {
            return attribute.getCodeName();
        }

        @Override
        public TurfOrDirtConditionCode convertToEntityAttribute(String dbData) {
            return CodeEnum.convertByCodeName(dbData, TurfOrDirtConditionCode.class);
        }
    }

}
