package org.uma.cloud.common.model;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.uma.cloud.common.code.RecordSpec;
import org.uma.cloud.common.utils.lang.ModelUtil;

import javax.persistence.AttributeConverter;
import javax.persistence.Column;
import javax.persistence.Converter;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@TypeDefs({
        @TypeDef(name = "list", typeClass = ListArrayType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@MappedSuperclass
public abstract class BaseModel implements Serializable {

    /**
     * レコード識別子
     */
    @Column(length = 2, nullable = false)
    private RecordSpec recordType;

    /**
     * JvLink側データ作成 タイムスタンプ
     */
    @Column(nullable = false)
    private LocalDate dataCreateDate;

    /**
     * ToString => Json format
     */
    @Override
    public String toString() {
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