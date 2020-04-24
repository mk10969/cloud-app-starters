package org.uma.cloud.common.code;

import com.fasterxml.jackson.annotation.JsonValue;
import org.uma.cloud.common.utils.constants.CodeEnum;

import java.util.Objects;

/**
 * 2302.騎乗資格コード
 */
public enum JockeyLicenseCode implements CodeEnum<Integer, JockeyLicenseCode> {

    /**
     * 未設備時のデフォルト値
     */
    DEFAULT(0, ""),
    BOTH(1, "平地・障害"),
    FLAT(2, "平地"),
    JUMP(3, "障害"),
    ;

    private final Integer code;
    private final String codeName;

    JockeyLicenseCode(Integer code, String codeName) {
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

    public static JockeyLicenseCode of(Integer code) {
        Objects.requireNonNull(code);
        if (DEFAULT.getCode().equals(code)) {
            return DEFAULT;
        }
        return CodeEnum.reversibleFindOne(code, JockeyLicenseCode.class);
    }

}
