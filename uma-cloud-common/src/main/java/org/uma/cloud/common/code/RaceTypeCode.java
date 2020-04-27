package org.uma.cloud.common.code;

import com.fasterxml.jackson.annotation.JsonValue;
import org.uma.cloud.common.utils.constants.CodeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

/**
 * 2005.競走種別コード
 */
public enum RaceTypeCode implements CodeEnum<Integer, RaceTypeCode> {

    /**
     * デフォルト "00"の文字列から、Integer 0 にコンバートする。
     */
    DEFAULT(0, "", ""),
    SARA2JUST(11, "サラ２才", "サラ系２歳"),
    SARA3JUST(12, "サラ３才", "サラ系３歳"),
    SARA3MORE(13, "サラ３上", "サラ系３歳以上"),
    SARA4MORE(14, "サラ４上", "サラ系４歳以上"),
    JSARA3MORE(18, "障害３上", "サラ障害３歳以上"),
    JSARA4MORE(19, "障害４上", "サラ障害４歳以上"),
    ARA2JUST(21, "アラ２才", "アラブ系２歳"),
    ARA3JUST(22, "アラ３才", "アラブ系３歳"),
    ARA3MORE(23, "アラ３上", "アラブ系３歳以上"),
    ARA4MORE(24, "アラ４上", "アラブ系４歳以上"),
    ;

    private final Integer code;
    private final String codeName;
    private final String codeNameFull;

    RaceTypeCode(Integer code, String codeName, String codeNameFull) {
        this.code = code;
        this.codeName = codeName;
        this.codeNameFull = codeNameFull;
    }

    @Override
    public Integer getCode() {
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

    public static RaceTypeCode of(Integer code) {
        Objects.requireNonNull(code);
        if (DEFAULT.getCode().equals(code)) {
            return DEFAULT;
        }
        return CodeEnum.reversibleFindOne(code, RaceTypeCode.class);
    }

    /**
     * Jpa enum converter impl
     */
    @Converter(autoApply = true)
    public static class converterImpl implements AttributeConverter<RaceTypeCode, String> {
        @Override
        public String convertToDatabaseColumn(RaceTypeCode attribute) {
            return attribute.getCodeName();
        }

        @Override
        public RaceTypeCode convertToEntityAttribute(String dbData) {
            return CodeEnum.convertOf(dbData, RaceTypeCode.class);
        }
    }

}
