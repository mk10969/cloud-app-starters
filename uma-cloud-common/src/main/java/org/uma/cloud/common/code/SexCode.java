package org.uma.platform.common.code;

import org.uma.platform.common.utils.constants.CodeEnum;

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

    private Integer code;
    private String codeName;

    SexCode(Integer code, String codeName) {
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

    public static SexCode of(Integer code) {
        Objects.requireNonNull(code);
        if (DEFAULT.getCode().equals(code)) {
            return DEFAULT;
        }
        return CodeEnum.reversibleFindOne(code, SexCode.class);
    }


}
