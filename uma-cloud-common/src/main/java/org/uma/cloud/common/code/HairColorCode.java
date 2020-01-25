package org.uma.platform.common.code;

import org.uma.platform.common.utils.constants.CodeEnum;

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

    private String code;
    private String codeName;

    HairColorCode(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public String getCodeName() {
        return this.codeName;
    }

    public static HairColorCode of(String code) {
        Objects.requireNonNull(code);
        if (DEFAULT.getCode().equals(code)) {
            return DEFAULT;
        }
        return CodeEnum.reversibleFindOne(code, HairColorCode.class);
    }

}
