package org.uma.platform.common.code;

import org.uma.platform.common.utils.constants.CodeEnum;

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

    private Integer code;
    private String codeName;

    EastOrWestBelongCode(Integer code, String codeName) {
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

    public static EastOrWestBelongCode of(Integer code) {
        Objects.requireNonNull(code);
        if (DEFAULT.getCode().equals(code)) {
            return DEFAULT;
        }
        return CodeEnum.reversibleFindOne(code, EastOrWestBelongCode.class);
    }
}
