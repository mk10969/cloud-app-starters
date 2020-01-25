package org.uma.platform.common.code;

import org.uma.platform.common.utils.constants.CodeEnum;

import java.util.Objects;

/**
 * 2003.グレードコード
 */
public enum RaceGradeCode implements CodeEnum<String, RaceGradeCode> {

    /**
     * 未設備時のデフォルト値
     */
    DEFAULT(" ", "一般競走"),
    G1("A", "G1（平地競走）"),
    G2("B", "G2（平地競走）"),
    G3("C", "G3（平地競走）"),
    OP1("D", "グレードのない重賞"),
    OP2("E", "重賞以外の特別競走"),
    JG1("F", "J･G1（障害競走）"),
    JG2("G", "J･G2（障害競走）"),
    JG3("H", "J･G3（障害競走）"),
    LISTED("L", "L（リステッド）"),

    ;

    private String code;
    private String gradeName;

    RaceGradeCode(String code, String gradeName) {
        this.code = code;
        this.gradeName = gradeName;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public String getGradeName() {
        return this.gradeName;
    }

    public static RaceGradeCode of(String code) {
        Objects.requireNonNull(code);
        if (DEFAULT.getCode().equals(code)) {
            return DEFAULT;
        }
        return CodeEnum.reversibleFindOne(code, RaceGradeCode.class);
    }


//    競走レベルをソートする。
//    public static List<> sort(){
//
//    }


}
