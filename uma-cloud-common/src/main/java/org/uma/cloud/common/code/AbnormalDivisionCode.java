package org.uma.platform.common.code;

import org.uma.platform.common.utils.constants.CodeEnum;

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

    private Integer code;
    private String codeName;
    private String codeNameEng;

    AbnormalDivisionCode(Integer code, String codeName, String codeNameEng) {
        this.code = code;
        this.codeName = codeName;
        this.codeNameEng = codeNameEng;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

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
        return CodeEnum.reversibleFindOne(code, AbnormalDivisionCode.class);
    }

}
