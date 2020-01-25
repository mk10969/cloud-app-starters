package org.uma.platform.common.code;

import org.uma.platform.common.utils.constants.CodeEnum;

import java.util.Objects;

/**
 * 2204.馬記号コード
 */
public enum HorseSignCode implements CodeEnum<String, HorseSignCode> {

    /**
     * 未設備時のデフォルト値
     */
    DEFAULT("00", ""),
    U01("01", "(抽)"),
    U02("02", "[抽]"),
    U03("03", "(父)"),
    U04("04", "(市)"),
    U05("05", "(地)"),
    U06("06", "(外)"),
    U07("07", "(父)(抽)"),
    U08("08", "(父)(市)"),
    U09("09", "(父)(地)"),
    U10("10", "(市)(地)"),
    U11("11", "(外)(地)"),
    U12("12", "(父)(市)(地)"),
    U15("15", "(招)"),
    U16("16", "(招)(外)"),
    U17("17", "(招)(父)"),
    U18("18", "(招)(市)"),
    U19("19", "(招)(父)(市)"),
    U20("20", "(父)(外)"),
    U21("21", "[地]"),
    U22("22", "(外)[地]"),
    U23("23", "(父)[地]"),
    U24("24", "(市)[地]"),
    U25("25", "(父)(市)[地]"),
    U26("26", "[外]"),
    U27("27", "(父)[外]"),
    U31("31", "(持)"),
    U40("40", "(父)(外)(地)"),
    U41("41", "(父)(外)[地]"),
    ;

    private String code;
    private String codeName;

    HorseSignCode(String code, String codeName) {
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

    public static HorseSignCode of(String code) {
        Objects.requireNonNull(code);
        if (DEFAULT.getCode().equals(code)) {
            return DEFAULT;
        }
        return CodeEnum.reversibleFindOne(code, HorseSignCode.class);
    }
}
