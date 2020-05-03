package org.uma.cloud.common.code;

import com.fasterxml.jackson.annotation.JsonValue;
import org.uma.cloud.common.utils.constants.CodeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;


/**
 * 2001.競馬場コード
 */
public enum RaceCourseCode implements CodeEnum<String, RaceCourseCode> {

    /**
     * デフォルト
     */
    DEFAULT("00", "", ""),
    SAPPORO("01", "札幌", "札幌競馬場"),
    HAKODATE("02", "函館", "函館競馬場"),
    FUKUSHIMA("03", "福島", "福島競馬場"),
    NIIGATA("04", "新潟", "新潟競馬場"),
    TOKYO("05", "東京", "東京競馬場"),
    NAKAYAMA("06", "中山", "中山競馬場"),
    CHUKYO("07", "中京", "中京競馬場"),
    KYOTO("08", "京都", "京都競馬場"),
    HANSHIN("09", "阪神", "阪神競馬場"),
    KOKURA("10", "小倉", "小倉競馬場"),
    MONBETSU("30", "門別", "門別競馬場"),
    KITAMI("31", "北見", "北見競馬場"),
    IWAMIZAWA("32", "岩見沢", "岩見沢競馬場"),
    OBIHIRO("33", "帯広", "帯広競馬場"),
    ASAHIKAWA("34", "旭川", "旭川競馬場"),
    MORIOKA("35", "盛岡", "盛岡競馬場"),
    MIZUSAWA("36", "水沢", "水沢競馬場"),
    KAMINOYAMA("37", "上山", "上山競馬場"),
    SANJYO("38", "三条", "三条競馬場"),
    ASHIKAGA("39", "足利", "足利競馬場"),
    UTSUNOMIYA("40", "宇都宮", "宇都宮競馬場"),
    TAKASAKI("41", "高崎", "高崎競馬場"),
    URAWA("42", "浦和", "浦和競馬場"),
    FUNABASHI("43", "船橋", "船橋競馬場"),
    OHI("44", "大井", "大井競馬場"),
    KAWASAKI("45", "川崎", "川崎競馬場"),
    KANAZAWA("46", "金沢", "金沢競馬場"),
    KASAMATSU("47", "笠松", "笠松競馬場"),
    NAGOYA("48", "名古屋", "名古屋競馬場"),
    KIMIIDERA("49", "紀三寺", "紀三井寺競馬場"),
    SONODA("50", "園田", "園田競馬場"),
    HIMEJI("51", "姫路", "姫路競馬場"),
    MASUDA("52", "益田", "益田競馬場"),
    FUKUYAMA("53", "福山", "福山競馬場"),
    KOCHI("54", "高知", "高知競馬場"),
    SAGA("55", "佐賀", "佐賀競馬場"),
    ARAO("56", "荒尾", "荒尾競馬場"),
    NAKATSU("57", "中津", "中津競馬場"),
    SAPPORO_NAR("58", "札幌（地方）", "札幌競馬場（地方競馬）"),
    HAKODATE_NAR("59", "函館（地方）", "函館競馬場（地方競馬）"),
    NIIGATA_NAR("60", "新潟（地方）", "新潟競馬場（地方競馬）"),
    CHUKYO_NAR("61", "中京（地方）", "中京競馬場（地方競馬）"),

    Other("A0", "他外", "他外国"),
    Japan("A2", "日本", "日本"),
    United("A4", "アメリ", "アメリカ"),
    Great("A6", "イギリ", "イギリス"),
    France("A8", "フラン", "フランス"),
    India("B0", "インド", "インド"),
    Ireland("B2", "アイル", "アイルランド"),
    NewZealand("B4", "ニュー", "ニュージーランド"),
    Australia("B6", "オース", "オーストラリア"),
    Canada("B8", "カナダ", "カナダ"),
    Italy("C0", "イタリ", "イタリア"),
    Germany("C2", "ドイツ", "ドイツ"),
    Oman("C5", "オマー", "オマーン"),
    Iraq("C6", "イラク", "イラク"),
    Arab("C7", "ア首", "アラブ首長国連邦"),
    Syrian("C8", "シリア", "シリア"),
    Sweden("D0", "スウェ", "スウェーデン"),
    Hungary("D2", "ハンガ", "ハンガリー"),
    Portugal("D4", "ポルト", "ポルトガル"),
    Russia("D6", "ロシア", "ロシア"),
    Uruguay("D8", "ウルグ", "ウルグアイ"),
    Peru("E0", "ペルー", "ペルー"),
    Argentina("E2", "アルゼ", "アルゼンチン"),
    Brazil("E4", "ブラジ", "ブラジル"),
    Belgium("E6", "ベルギ", "ベルギー"),
    Turkey("E8", "トルコ", "トルコ"),
    Korea("F0", "韓国", "韓国"),
    China("F1", "中国", "中国"),
    Chile("F2", "チリ", "チリ"),
    Panama("F8", "パナマ", "パナマ"),
    Hong("G0", "香港", "香港"),
    Spain("G2", "スペイ", "スペイン"),
    West("H0", "西独", "西ドイツ"),
    South("H2", "南アフ", "南アフリカ"),
    Switzerland("H4", "スイス", "スイス"),
    Monaco("H6", "モナコ", "モナコ"),
    Philippines("H8", "フィリ", "フィリピン"),
    Puerto("I0", "プエル", "プエルトリコ"),
    Colombia("I2", "コロン", "コロンビア"),
    Czechoslovakia("I4", "チェコ", "チェコスロバキア"),
    Czech("I6", "チェコ", "チェコ"),
    Slovakia("I8", "スロバ", "スロバキア"),
    Ecuador("J0", "エクア", "エクアドル"),
    Greece("J2", "ギリシ", "ギリシャ"),
    Malaysia("J4", "マレー", "マレーシア"),
    Mexico("J6", "メキシ", "メキシコ"),
    Morocco("J8", "モロッ", "モロッコ"),
    Pakistan("K0", "パキス", "パキスタン"),
    Poland("K2", "ポーラ", "ポーランド"),
    Paraguay("K4", "パラグ", "パラグアイ"),
    Saudi("K6", "サウジ", "サウジアラビア"),
    Cyprus("K8", "キプロ", "キプロス"),
    Thailand("L0", "タイ", "タイ"),
    Ukraine("L2", "ウクラ", "ウクライナ"),
    Venezuela("L4", "ベネゼ", "ベネズエラ"),
    Yugoslavia("L6", "ユーゴ", "ユーゴスラビア"),
    Denmark("L8", "デンマ", "デンマーク"),
    Singapore("M0", "シンガ", "シンガポール"),
    Macau("M2", "マカオ", "マカオ"),
    Austria("M4", "墺国", "オーストリア"),
    Jordan("M6", "ヨルダ", "ヨルダン"),
    Qatar("M8", "カタル", "カタール"),
    ;

    private final String code;
    private final String codeName;
    private final String codeNameFull;

    RaceCourseCode(String code, String codeName, String codeNameFull) {
        this.code = code;
        this.codeName = codeName;
        this.codeNameFull = codeNameFull;
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

    public String getCodeNameFull() {
        return this.codeNameFull;
    }


    public static RaceCourseCode of(String code) {
        Objects.requireNonNull(code);
        if (DEFAULT.getCode().equals(code)) {
            return DEFAULT;
        }
        return CodeEnum.convertByCode(code, RaceCourseCode.class);
    }

    /**
     * Jpa enum converter impl
     */
    @Converter(autoApply = true)
    public static class converterImpl implements AttributeConverter<RaceCourseCode, String> {
        @Override
        public String convertToDatabaseColumn(RaceCourseCode attribute) {
            return attribute.getCodeName();
        }

        @Override
        public RaceCourseCode convertToEntityAttribute(String dbData) {
            return CodeEnum.convertByCodeName(dbData, RaceCourseCode.class);
        }
    }


//    public static List<RaceCourseCode> getDomesticCodes() {
//        return Stream.of(RaceCourseCode.values())
//                .filter(i -> StringUtils.isNumeric(i.getCode()))
//                .collect(ImmutableList.toImmutableList());
//    }

//    public static List<RaceCourseCode> getCentralHorseRaceCodes() {
//        return Stream.of(RaceCourseCode.values())
//                .filter(i -> StringUtils.isNumeric(i.getCode()))
//                .collect(ImmutableList.toImmutableList());
//    }


}
