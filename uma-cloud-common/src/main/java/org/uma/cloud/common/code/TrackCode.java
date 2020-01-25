package org.uma.platform.common.code;

import org.uma.platform.common.utils.constants.CodeEnum;

import java.util.Objects;

/**
 * 2009.トラックコード
 */
public enum TrackCode implements CodeEnum<Integer, TrackCode> {

    /**
     * デフォルト "00"の文字列から、Integer 0 にコンバートする。
     */
    DEFAULT(0, ""),
    TURF10(10, "芝・直"),
    TURF11(11, "芝・左"),
    TURF12(12, "芝・左外"),
    TURF13(13, "芝・左内→外"),
    TURF14(14, "芝・左外→内"),
    TURF15(15, "芝・左内２周"),
    TURF16(16, "芝・左外２周"),
    TURF17(17, "芝・右"),
    TURF18(18, "芝・右外"),
    TURF19(19, "芝・右内→外"),
    TURF20(20, "芝・右外→内"),
    TURF21(21, "芝・右内２周"),
    TURF22(22, "芝・右外２周"),
    DIRT23(23, "ダート・左"),
    DIRT24(24, "ダート・右"),
    DIRT25(25, "ダート・左内"),
    DIRT26(26, "ダート・右外"),
    SAND27(27, "サンド・左"),
    SAND28(28, "サンド・右"),
    DIRT29(29, "ダート・直"),
    JUMP51(51, "芝・襷"),
    JUMP52(52, "芝→ダート"),
    JUMP53(53, "芝・左"),
    JUMP54(54, "芝"),
    JUMP55(55, "芝・外"),
    JUMP56(56, "芝・外→内"),
    JUMP57(57, "芝・内→外"),
    JUMP58(58, "芝・内２周"),
    JUMP59(59, "芝・外２周"),
    ;

    private Integer code;
    private String codeName;

    TrackCode(Integer code, String codeName) {
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

    public static TrackCode of(Integer code) {
        Objects.requireNonNull(code);
        if (DEFAULT.getCode().equals(code)) {
            return DEFAULT;
        }
        return CodeEnum.reversibleFindOne(code, TrackCode.class);
    }


}
