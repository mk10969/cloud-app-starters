package org.uma.cloud.common.model;

import lombok.Getter;
import org.uma.cloud.common.recordSpec.RecordSpec;

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
     * 繁殖登録番号 8桁
     */
    @Id
    private Integer breedingNo;

    @Column(length = 30)
    private String ancestryId;

    @Column(length = 36)
    private String ancestryName;

    @Column(length = 3000)
    private String ancestryDescription;

}
