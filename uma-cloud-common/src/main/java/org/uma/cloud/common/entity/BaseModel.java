package org.uma.cloud.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.uma.cloud.common.utils.lang.ModelUtil;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Objects;

@TypeDefs({
        @TypeDef(name = "list", typeClass = ListArrayType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@MappedSuperclass
@JsonIgnoreProperties(value = {"primaryKey", "recordType"})
public abstract class BaseModel implements Serializable {

    /**
     * データ区分  ＠Dataで自動Impl
     */
    public abstract String getDataDiv();

    /**
     * 各モデルのPKを返す。
     */
    public abstract Object getPrimaryKey();

    /**
     * 各モデルのクラス名を返す。
     */
    public String getRecordType() {
        String classNameContainPackage = Objects.requireNonNullElseGet(
                getClass().getEnclosingClass(), this::getClass).getName();
        return Arrays.stream(classNameContainPackage.split("\\."))
                .reduce((first, second) -> second)
                .orElse("");
    }

    /**
     * ToJson => Json format
     */
    public String toJson() {
        return ModelUtil.toJson(this);
    }


    /**
     * Jpa enum converter impl
     * <p>
     * 置き場所がないので、一旦ここにおいておく。
     */
    @Converter(autoApply = true)
    public static class LocalTimeAttributeConverter implements AttributeConverter<LocalTime, Time> {

        @Override
        public Time convertToDatabaseColumn(LocalTime localTime) {
            long epochMilli = localTime.atDate(LocalDate.EPOCH)
                    .atZone(java.time.ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli();
            return new Time(epochMilli);
        }

        @Override
        public LocalTime convertToEntityAttribute(Time time) {
            LocalTime localTime = LocalTime.parse(new SimpleDateFormat("HH:mm:ss.SSS").format(time));
            return (time == null ? null : localTime);
        }
    }

}