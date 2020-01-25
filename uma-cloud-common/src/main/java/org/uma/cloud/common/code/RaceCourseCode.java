package org.uma.platform.common.code;

import org.uma.platform.common.utils.constants.CodeEnum;

import java.util.Objects;


/**
 * 2001.競馬場コード
 */
public enum RaceCourseCode implements CodeEnum<String, RaceCourseCode> {

    /**
     * デフォルト
     */
    DEFAULT("00", "", ""),
    SAPPORO("01", "札幌競馬場", "札幌"),
    HAKODATE("02", "函館競馬場", "函館"),
    FUKUSHIMA("03", "福島競馬場", "福島"),
    NIIGATA("04", "新潟競馬場", "新潟"),
    TOKYO("05", "東京競馬場", "東京"),
    NAKAYAMA("06", "中山競馬場", "中山"),
    CHUKYO("07", "中京競馬場", "中京"),
    KYOTO("08", "京都競馬場", "京都"),
    HANSHIN("09", "阪神競馬場", "阪神"),
    KOKURA("10", "小倉競馬場", "小倉"),
    MONBETSU("30", "門別競馬場", "門別"),
    KITAMI("31", "北見競馬場", "北見"),
    IWAMIZAWA("32", "岩見沢競馬場", "岩見沢"),
    OBIHIRO("33", "帯広競馬場", "帯広"),
    ASAHIKAWA("34", "旭川競馬場", "旭川"),
    MORIOKA("35", "盛岡競馬場", "盛岡"),
    MIZUSAWA("36", "水沢競馬場", "水沢"),
    KAMINOYAMA("37", "上山競馬場", "上山"),
    SANJYO("38", "三条競馬場", "三条"),
    ASHIKAGA("39", "足利競馬場", "足利"),
    UTSUNOMIYA("40", "宇都宮競馬場", "宇都宮"),
    TAKASAKI("41", "高崎競馬場", "高崎"),
    URAWA("42", "浦和競馬場", "浦和"),
    FUNABASHI("43", "船橋競馬場", "船橋"),
    OHI("44", "大井競馬場", "大井"),
    KAWASAKI("45", "川崎競馬場", "川崎"),
    KANAZAWA("46", "金沢競馬場", "金沢"),
    KASAMATSU("47", "笠松競馬場", "笠松"),
    NAGOYA("48", "名古屋競馬場", "名古屋"),
    KIMIIDERA("49", "紀三井寺競馬場", "紀三寺"),
    SONODA("50", "園田競馬場", "園田"),
    HIMEJI("51", "姫路競馬場", "姫路"),
    MASUDA("52", "益田競馬場", "益田"),
    FUKUYAMA("53", "福山競馬場", "福山"),
    KOCHI("54", "高知競馬場", "高知"),
    SAGA("55", "佐賀競馬場", "佐賀"),
    ARAO("56", "荒尾競馬場", "荒尾"),
    NAKATSU("57", "中津競馬場", "中津"),
    SAPPORO_NAR("58", "札幌競馬場（地方競馬）", "札幌（地方）"),
    HAKODATE_NAR("59", "函館競馬場（地方競馬）", "函館（地方）"),
    NIIGATA_NAR("60", "新潟競馬場（地方競馬）", "新潟（地方）"),
    CHUKYO_NAR("61", "中京競馬場（地方競馬）", "中京（地方）"),

    Other("A0", "他外国", "他外"),
    Japan("A2", "日本", "日本"),
    United("A4", "アメリカ", "アメリ"),
    Great("A6", "イギリス", "イギリ"),
    France("A8", "フランス", "フラン"),
    India("B0", "インド", "インド"),
    Ireland("B2", "アイルランド", "アイル"),
    NewZealand("B4", "ニュージーランド", "ニュー"),
    Australia("B6", "オーストラリア", "オース"),
    Canada("B8", "カナダ", "カナダ"),
    Italy("C0", "イタリア", "イタリ"),
    Germany("C2", "ドイツ", "ドイツ"),
    Oman("C5", "オマーン", "オマー"),
    Iraq("C6", "イラク", "イラク"),
    Arab("C7", "アラブ首長国連邦", "ア首"),
    Syrian("C8", "シリア", "シリア"),
    Sweden("D0", "スウェーデン", "スウェ"),
    Hungary("D2", "ハンガリー", "ハンガ"),
    Portugal("D4", "ポルトガル", "ポルト"),
    Russia("D6", "ロシア", "ロシア"),
    Uruguay("D8", "ウルグアイ", "ウルグ"),
    Peru("E0", "ペルー", "ペルー"),
    Argentina("E2", "アルゼンチン", "アルゼ"),
    Brazil("E4", "ブラジル", "ブラジ"),
    Belgium("E6", "ベルギー", "ベルギ"),
    Turkey("E8", "トルコ", "トルコ"),
    Korea("F0", "韓国", "韓国"),
    China("F1", "中国", "中国"),
    Chile("F2", "チリ", "チリ"),
    Panama("F8", "パナマ", "パナマ"),
    Hong("G0", "香港", "香港"),
    Spain("G2", "スペイン", "スペイ"),
    West("H0", "西ドイツ", "西独"),
    South("H2", "南アフリカ", "南アフ"),
    Switzerland("H4", "スイス", "スイス"),
    Monaco("H6", "モナコ", "モナコ"),
    Philippines("H8", "フィリピン", "フィリ"),
    Puerto("I0", "プエルトリコ", "プエル"),
    Colombia("I2", "コロンビア", "コロン"),
    Czechoslovakia("I4", "チェコスロバキア", "チェコ"),
    Czech("I6", "チェコ", "チェコ"),
    Slovakia("I8", "スロバキア", "スロバ"),
    Ecuador("J0", "エクアドル", "エクア"),
    Greece("J2", "ギリシャ", "ギリシ"),
    Malaysia("J4", "マレーシア", "マレー"),
    Mexico("J6", "メキシコ", "メキシ"),
    Morocco("J8", "モロッコ", "モロッ"),
    Pakistan("K0", "パキスタン", "パキス"),
    Poland("K2", "ポーランド", "ポーラ"),
    Paraguay("K4", "パラグアイ", "パラグ"),
    Saudi("K6", "サウジアラビア", "サウジ"),
    Cyprus("K8", "キプロス", "キプロ"),
    Thailand("L0", "タイ", "タイ"),
    Ukraine("L2", "ウクライナ", "ウクラ"),
    Venezuela("L4", "ベネズエラ", "ベネゼ"),
    Yugoslavia("L6", "ユーゴスラビア", "ユーゴ"),
    Denmark("L8", "デンマーク", "デンマ"),
    Singapore("M0", "シンガポール", "シンガ"),
    Macau("M2", "マカオ", "マカオ"),
    Austria("M4", "オーストリア", "墺国"),
    Jordan("M6", "ヨルダン", "ヨルダ"),
    Qatar("M8", "カタール", "カタル"),
    ;

    private String code;
    private String raceCourseName;
    private String raceCourseNameShort;

    RaceCourseCode(String code, String raceCourseName, String raceCourseNameShort) {
        this.code = code;
        this.raceCourseName = raceCourseName;
        this.raceCourseNameShort = raceCourseNameShort;
    }

    public String getCode() {
        return this.code;
    }

    public String getRaceCourseName() {
        return this.raceCourseName;
    }

    public String getRaceCourseNameShort() {
        return this.raceCourseNameShort;
    }


    public static RaceCourseCode of(String code) {
        Objects.requireNonNull(code);
        if (DEFAULT.getCode().equals(code)) {
            return DEFAULT;
        }
        return CodeEnum.reversibleFindOne(code, RaceCourseCode.class);
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
