package org.uma.cloud.common.model;

import lombok.Getter;
import org.uma.cloud.common.code.RecordSpec;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * {@link RecordSpec.BT}
 */
@Getter
@Entity
@Table
public class BloodAncestry extends BaseModel {

    /**
     * データ区分
     */
    @Column(length = 1, nullable = false)
    private String dataDiv;

    /**
     * 繁殖登録番号 8桁
     */
    @Id
    @Column(nullable = false)
    private Integer breedingNo;

    @Column(length = 30, nullable = false)
    private String ancestryId;

    @Column(length = 36, nullable = false)
    private String ancestryName;

    @Column(length = 3000, nullable = false)
    private String ancestryDescription;

}
