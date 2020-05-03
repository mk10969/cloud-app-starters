package org.uma.cloud.common.model;

import lombok.Getter;
import org.uma.cloud.common.code.RecordSpec;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * {@link RecordSpec.BN}
 */
@Getter
@Entity
@Table
public class Owner extends BaseModel {

    /**
     * データ区分
     */
    @Column(length = 1)
    private String dataDiv;

    /**
     * 馬主コード 6桁
     */
    @Id
    private Integer ownerCd;

    @Column(length = 64)
    private String ownerNameWithCorp;

    @Column(length = 64)
    private String ownerNameWithoutCorp;

    @Column(length = 50)
    private String ownerNameHalfKana;

    @Column(length = 100)
    private String ownerNameEng;

    @Column(length = 60)
    private String clothingMark;

}
