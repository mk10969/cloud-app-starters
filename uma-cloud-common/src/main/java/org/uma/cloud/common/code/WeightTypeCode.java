package org.uma.cloud.common.code;

import com.fasterxml.jackson.annotation.JsonValue;
import org.uma.cloud.common.utils.constants.CodeEnum;

import java.util.Objects;

/**
 * 2008.重量種別コード
 */
public enum WeightTypeCode implements CodeEnum<Integer, WeightTypeCode> {

    /**
     * 未設備時のデフォルト値
     */
    DEFAULT(0, ""),
    HANDICAP(1, "ハンデ"),
    SPECIAL(2, "別定"),
    HORSEAGE(3, "馬齢"),
    FIXED(4, "定量"),
    ;

    private final Integer code;
    private final String codeName;

    WeightTypeCode(Integer code, String codeName) {
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

    public static WeightTypeCode of(Integer code) {
        Objects.requireNonNull(code);
        if (DEFAULT.getCode().equals(code)) {
            return DEFAULT;
        }
        return CodeEnum.reversibleFindOne(code, WeightTypeCode.class);
    }
}
