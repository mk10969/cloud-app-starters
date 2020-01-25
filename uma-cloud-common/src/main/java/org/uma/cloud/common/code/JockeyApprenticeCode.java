package org.uma.platform.common.code;

import org.uma.platform.common.utils.constants.CodeEnum;

import java.util.Objects;

/**
 * 2303.騎手見習コード
 */
public enum JockeyApprenticeCode implements CodeEnum<Integer, JockeyApprenticeCode> {

    /**
     * 未設備時のデフォルト値
     */
    DEFAULT(0, ""),
    _1Kg(1, "☆"),
    _2Kg(2, "△"),
    _3Kg(3, "▲"),

    /**
     * 女性騎手専用コード
     */
    _4Kg_F(4, "★"),
    _2Kg_F(9, "◇")

    ;


    private Integer code;
    private String codeMark;

    JockeyApprenticeCode(Integer code, String codeMark) {
        this.code = code;
        this.codeMark = codeMark;
    }

    @Override
    public Integer getCode() {
        return this.code;
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

}
