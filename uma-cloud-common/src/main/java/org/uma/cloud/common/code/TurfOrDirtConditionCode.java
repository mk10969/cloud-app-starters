package org.uma.platform.common.code;

import org.uma.platform.common.utils.constants.CodeEnum;

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

    private Integer code;
    private String codeName;

    TurfOrDirtConditionCode(Integer code, String codeName) {
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

    public static TurfOrDirtConditionCode of(Integer code) {
        Objects.requireNonNull(code);
        if (DEFAULT.getCode().equals(code)) {
            return DEFAULT;
        }
        return CodeEnum.reversibleFindOne(code, TurfOrDirtConditionCode.class);
    }

}
