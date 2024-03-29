package org.uma.cloud.common.code;

import com.fasterxml.jackson.annotation.JsonValue;
import org.uma.cloud.common.utils.constants.CodeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

/**
 * 2006.競走記号コード
 */
public enum RaceSignCode implements CodeEnum<String, RaceSignCode> {

    /**
     * デフォルト
     */
    DEFAULT("000", ""),
    O01("001", "(指定)"),
    O02("002", "見習・若手騎手"),
    O03("003", "[指定] "),
    O04("004", "(特指) "),
    O20("020", "牝"),
    O21("021", "牝 (指定)"),
    O23("023", "牝 [指定] "),
    O24("024", "牝 (特指)"),
    O30("030", "牡・ｾﾝ"),
    O31("031", "牡・ｾﾝ (指定)"),
    O33("033", "牡・ｾﾝ [指定]"),
    O34("034", "牡・ｾﾝ (特指)"),
    O40("040", "牡・牝"),
    O41("041", "牡・牝 (指定)"),
    O43("043", "牡・牝 [指定] "),
    O44("044", "牡・牝 (特指)"),
    A00("A00", "(混合) "),
    A01("A01", "(混合)(指定)"),
    A02("A02", "(混合) 見習・若手騎手 "),
    A03("A03", "(混合)[指定] "),
    A04("A04", "(混合)(特指)"),
    A10("A10", "(混合) 牡"),
    A11("A11", "(混合) 牡 (指定)"),
    A13("A13", "(混合) 牡 [指定]"),
    A14("A14", "(混合) 牡 (特指)"),
    A20("A20", "(混合) 牝 "),
    A21("A21", "(混合) 牝 (指定)"),
    A23("A23", "(混合) 牝 [指定] "),
    A24("A24", "(混合) 牝 (特指)"),
    A30("A30", "(混合) 牡・ｾﾝ"),
    A31("A31", "(混合) 牡・ｾﾝ (指定)"),
    A33("A33", "(混合) 牡・ｾﾝ [指定] "),
    A34("A34", "(混合) 牡・ｾﾝ (特指)"),
    A40("A40", "(混合) 牡・牝"),
    A41("A41", "(混合) 牡・牝 (指定)"),
    B00("B00", "(父)"),
    B01("B01", "(父)(指定)"),
    B03("B03", "(父)[指定] "),
    B04("B04", "(父)(特指)"),
    C00("C00", "(市)"),
    C01("C01", "(市)(指定) "),
    C03("C03", "(市)[指定] "),
    C04("C04", "(市)(特指)"),
    D00("D00", "(抽)"),
    D01("D01", "(抽)(指定)"),
    D03("D03", "(抽)[指定]"),
    E00("E00", "[抽] "),
    E01("E01", "[抽](指定)"),
    E03("E03", "[抽][指定] "),
    F00("F00", "(市)(抽)"),
    F01("F01", "(市)(抽)(指定)"),
    F03("F03", "(市)(抽)[指定]"),
    F04("F04", "(市)(抽)(特指)"),
    G00("G00", "(抽) 関西配布馬 "),
    G01("G01", "(抽) 関西配布馬 (指定)"),
    G03("G03", "(抽) 関西配布馬 [指定] "),
    H00("H00", "(抽) 関東配布馬"),
    H01("H01", "(抽) 関東配布馬 (指定)"),
    I00("I00", "[抽] 関西配布馬"),
    I01("I01", "[抽] 関西配布馬 (指定)"),
    I03("I03", "[抽] 関西配布馬 [指定] "),
    J00("J00", "[抽] 関東配布馬"),
    J01("J01", "[抽] 関東配布馬 (指定)"),
    K00("K00", "(市)(抽) 関西配布馬"),
    K01("K01", "(市)(抽) 関西配布馬 (指定)"),
    K03("K03", "(市)(抽) 関西配布馬 [指定] "),
    L00("L00", "(市)(抽) 関東配布馬"),
    L01("L01", "(市)(抽) 関東配布馬 (指定)"),
    L03("L03", "(市)(抽) 関東配布馬 [指定] "),
    M00("M00", "九州産馬"),
    M01("M01", "九州産馬 (指定)"),
    M03("M03", "九州産馬 [指定] "),
    M04("M04", "九州産馬 (特指)"),
    N00("N00", "(国際)"),
    N01("N01", "(国際)(指定)"),
    N03("N03", "(国際)[指定]"),
    N04("N04", "(国際)(特指)"),
    N20("N20", "(国際) 牝"),
    N21("N21", "(国際) 牝 (指定)"),
    N23("N23", "(国際) 牝 [指定]"),
    N24("N24", "(国際) 牝 (特指)"),
    N30("N30", "(国際) 牡・ｾﾝ"),
    N31("N31", "(国際) 牡・ｾﾝ (指定)"),
    N40("N40", "(国際) 牡・牝"),
    N41("N41", "(国際) 牡・牝 (指定)"),
    N44("N44", "(国際) 牡・牝 (特指)"),
    ;

    private final String code;
    private final String codeName;

    RaceSignCode(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    @JsonValue
    public String getCodeName() {
        return this.codeName;
    }

    public static RaceSignCode of(String code) {
        Objects.requireNonNull(code);
        if (DEFAULT.getCode().equals(code)) {
            return DEFAULT;
        }
        return CodeEnum.convertByCode(code, RaceSignCode.class);
    }

    /**
     * Jpa enum converter impl
     */
    @Converter(autoApply = true)
    public static class converterImpl implements AttributeConverter<RaceSignCode, String> {
        @Override
        public String convertToDatabaseColumn(RaceSignCode attribute) {
            return attribute.getCodeName();
        }

        @Override
        public RaceSignCode convertToEntityAttribute(String dbData) {
            return CodeEnum.convertByCodeName(dbData, RaceSignCode.class);
        }
    }

}
