package org.uma.cloud.common.model;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.annotations.UpdateTimestamp;
import org.uma.cloud.common.code.RecordSpec;
import org.uma.cloud.common.utils.lang.ModelUtil;

import javax.persistence.Column;
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
public abstract class BaseModel implements Serializable {

    /**
     * レコード識別子
     */
    @Column(length = 2)
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
    private LocalDateTime myCreateDateTime;

    /**
     * Myデータ更新日時
     */
    @UpdateTimestamp
    private LocalDateTime myUpdateDateTime;


//    /**
//     * this.dataDiv == "0" --> レース中止
//     * this.dataDiv == "9" --> 該当レコード削除（提供ミス）
//     */
//    public abstract boolean isNecessary();
//
//
//    public abstract T getPrimaryKey();


    /**
     * ToString => Json format
     */
    @Override
    public String toString() {
        return ModelUtil.toJson(this);
    }

}