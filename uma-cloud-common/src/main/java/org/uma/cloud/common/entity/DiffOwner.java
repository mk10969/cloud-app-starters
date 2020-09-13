package org.uma.cloud.common.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
public class DiffOwner extends BaseModel {

    /**
     * データ区分
     */
    @Column(length = 1, nullable = false)
    private String dataDiv;

    /**
     * 馬主コード 6桁
     */
    @Id
    @Column(nullable = false)
    private Integer ownerCd;

    @Column(length = 64, nullable = false)
    private String ownerNameWithCorp;

    @Column(length = 64, nullable = false)
    private String ownerNameWithoutCorp;

    @Column(length = 50, nullable = false)
    private String ownerNameHalfKana;

    @Column(length = 100, nullable = false)
    private String ownerNameEng;

    @Column(length = 60, nullable = false)
    private String clothingMark;

}
