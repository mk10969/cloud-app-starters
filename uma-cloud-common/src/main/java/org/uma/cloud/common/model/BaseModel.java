package org.uma.cloud.common.model;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.uma.cloud.common.code.RecordSpec;
import org.uma.cloud.common.utils.lang.ModelUtil;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDate;

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
    @Column(length = 2)
    private RecordSpec recordType;

    /**
     * JvLink側データ作成 タイムスタンプ
     */
    private LocalDate dataCreateDate;

    /**
     * ToString => Json format
     */
    @Override
    public String toString() {
        return ModelUtil.toJson(this);
    }

}