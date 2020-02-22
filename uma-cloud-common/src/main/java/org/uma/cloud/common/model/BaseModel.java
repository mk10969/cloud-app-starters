package org.uma.cloud.common.model;

import lombok.Getter;
import lombok.Setter;
import org.uma.cloud.common.utils.lang.JvLinkModelUtil;
import org.uma.cloud.common.recordSpec.RecordSpec;

import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
public class BaseModel implements Serializable {

    /**
     * レコード識別子
     */
    private RecordSpec recordType;

    /**
     * データ区分
     */
    private String dataDiv;

    /**
     * データ作成 タイムスタンプ
     */
    private LocalDate dataCreateDate;

    /**
     * ToString => Json format
     */
    @Override
    public String toString() {
        return JvLinkModelUtil.toJson(this);
    }

}
