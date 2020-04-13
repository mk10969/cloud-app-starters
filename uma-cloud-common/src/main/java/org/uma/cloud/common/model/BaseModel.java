package org.uma.cloud.common.model;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.annotations.UpdateTimestamp;
import org.uma.cloud.common.recordSpec.RecordSpec;
import org.uma.cloud.common.utils.lang.ModelUtil;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@TypeDefs({
        @TypeDef(name = "list", typeClass = ListArrayType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@MappedSuperclass
public class BaseModel implements Serializable {

    /**
     * レコード識別子
     */
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private RecordSpec recordType;

    /**
     * データ区分
     */
    @Column(length = 1)
    private String dataDiv;

    /**
     * JvLink側データ作成 タイムスタンプ
     */
    private LocalDate dataCreateDate;

    /**
     * Myデータ作成日時
     */
    @CreationTimestamp
    private LocalDateTime createDateTime;

    /**
     * Myデータ更新日時
     */
    @UpdateTimestamp
    private LocalDateTime updateDateTime;


    /**
     * this.dataDiv == "0" --> レース中止
     * this.dataDiv == "9" --> 該当レコード削除（提供ミス）
     */
    public boolean isNecessary() {
        return !("0".equals(this.dataDiv) || "9".equals(this.dataDiv));
    }

    /**
     * ToString => Json format
     */
    @Override
    public String toString() {
        return ModelUtil.toJson(this);
    }

}