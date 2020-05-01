package org.uma.cloud.common.code;

import com.fasterxml.jackson.annotation.JsonValue;
import org.uma.cloud.common.utils.constants.CodeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

public enum RecordSpec implements CodeEnum<String, RecordSpec> {

    TK("TK", "特別登録馬"),
    RA("RA", "レース詳細"),
    SE("SE", "馬毎レース情報"),
    HR("HR", "払戻"),
    H1("H1", "票数(3連単以外)"),
    H6("H6", "票数(3連単)"),
    O1("O1", "オッズ(単勝, 複勝, 枠連)"),
    O2("O2", "オッズ(馬連)"),
    O3("O3", "オッズ(ワイド)"),
    O4("O4", "オッズ(馬単)"),
    O5("O5", "オッズ(3連複)"),
    O6("O6", "オッズ(3連単)"),
    UM("UM", "競走馬マスタ"),
    KS("KS", "騎手マスタ"),
    CH("CH", "調教師マスタ"),
    BR("BR", "生産者マスタ"),
    BN("BN", "馬主マスタ"),
    HN("HN", "繁殖馬マスタ"),
    SK("SK", "産駒マスタ"),
    CK("CK", "出走別着度数"),
    RC("RC", "レコードマスタ"),
    HC("HC", "坂路調教"),
    HS("HS", "競走馬市場取引価格"),
    HY("HY", "馬名の意味由来情報"),
    YS("YS", "開催スケジュール"),
    BT("BT", "系統情報"),
    CS("CS", "コース情報"),
    DM("DM", "タイム型データマイニング予想"),
    TM("TM", "対戦型データマイニング予想"),
    WF("WF", "重勝式(WIN5)"),
    JG("JG", "競走馬除外情報"),
    WH("WH", "馬体重"),
    WE("WE", "天候馬場状態"),
    AV("AV", "出走取消競争除外"),
    JC("JC", "騎手変更"),
    TC("TC", "発走時刻変更"),
    CC("CC", "コース変更"),
    ;

    private final String code;
    private final String codeName;

    RecordSpec(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    @Override
    @JsonValue
    public String getCode() {
        return this.code;
    }

    @Override
    public String getCodeName() {
        return this.codeName;
    }


    public static RecordSpec of(String recordSpec) {
        Objects.requireNonNull(recordSpec);
        return CodeEnum.reversibleFindOne(recordSpec, RecordSpec.class);
    }

    /**
     * Jpa enum converter impl
     */
    @Converter(autoApply = true)
    public static class converterImpl implements AttributeConverter<RecordSpec, String> {
        @Override
        public String convertToDatabaseColumn(RecordSpec attribute) {
            return attribute.getCode();
        }

        @Override
        public RecordSpec convertToEntityAttribute(String dbData) {
            return CodeEnum.convertOf(dbData, RecordSpec.class);
        }
    }
}
