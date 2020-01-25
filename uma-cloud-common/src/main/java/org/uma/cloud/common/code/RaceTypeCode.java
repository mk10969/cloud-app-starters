package org.uma.platform.common.code;

import org.uma.platform.common.utils.constants.CodeEnum;

import java.util.Objects;


/**
 * 2005.競走種別コード
 */
public enum RaceTypeCode implements CodeEnum<Integer, RaceTypeCode> {

    /**
     * デフォルト "00"の文字列から、Integer 0 にコンバートする。
     */
    DEFAULT(0, "", ""),
    SARA2JUST(11, "サラ系２歳", "サラ２才"),
    SARA3JUST(12, "サラ系３歳", "サラ３才"),
    SARA3MORE(13, "サラ系３歳以上", "サラ３上"),
    SARA4MORE(14, "サラ系４歳以上", "サラ４上"),
    JSARA3MORE(18, "サラ障害３歳以上", "障害３上"),
    JSARA4MORE(19, "サラ障害４歳以上", "障害４上"),
    ARA2JUST(21, "アラブ系２歳", "アラ２才"),
    ARA3JUST(22, "アラブ系３歳", "アラ３才"),
    ARA3MORE(23, "アラブ系３歳以上", "アラ３上"),
    ARA4MORE(24, "アラブ系４歳以上", "アラ４上"),
    ;

    private Integer code;
    private String raceTypeName;
    private String raceTypeNameShort;

    RaceTypeCode(Integer code, String raceTypeName, String raceTypeNameShort) {
        this.code = code;
        this.raceTypeName = raceTypeName;
        this.raceTypeNameShort = raceTypeNameShort;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    public String getRaceTypeName() {
        return this.raceTypeName;
    }

    public String getRaceTypeNameShort() {
        return this.raceTypeNameShort;
    }

    public static RaceTypeCode of(Integer code) {
        Objects.requireNonNull(code);
        if (DEFAULT.getCode().equals(code)) {
            return DEFAULT;
        }
        return CodeEnum.reversibleFindOne(code, RaceTypeCode.class);
    }
}
