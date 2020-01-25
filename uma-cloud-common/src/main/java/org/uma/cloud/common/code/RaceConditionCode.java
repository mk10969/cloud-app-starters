//package org.uma.daiwaScarlet.code;
//
//
//import org.uma.vodka.common.constants.CodeEnum;
//
///**
// * 2007.競走条件コード
// */
//public enum RaceConditionCode implements CodeEnum<String, RaceConditionCode> {
//
//    /**
//     * 未設備時のデフォルト値
//     */
//    DEFAULT("000", ""),
//    LESS100("001", "１００万円以下"),
//    LESS200("002", "２００万円以下"),
//    LESS300("003", "３００万円以下"),
//    LESS9900("099", "９９００万円以下"),
//    LESS10000("100", "１億円以下"),
//    NEWCOMER("701", "新馬"),
//    UNRACED("702", "未出走"),
//    MAIDEN("703", "未勝利"),
//    OPEN("999", "オープン"),
//    ;
//
//    private String code;
//    private String codeName;
//
//    RaceConditionCode(String code, String codeName) {
//        this.code = code;
//        this.codeName = codeName;
//    }
//
//    @Override
//    public String getCode() {
//        return this.code;
//    }
//
//    public String getCodeName() {
//        return this.codeName;
//    }
//}
