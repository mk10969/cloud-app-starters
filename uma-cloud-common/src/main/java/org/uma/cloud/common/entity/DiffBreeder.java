package org.uma.cloud.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class DiffBreeder extends BaseModel {

    /**
     * データ区分
     */
    @Column(length = 1, nullable = false)
    private String dataDiv;

    /**
     * 生産者コード 6桁
     */
    @Id
    @Column(nullable = false)
    private Integer breederCd;

    @Column(length = 70, nullable = false)
    private String breederNameWithCorp;

    @Column(length = 70, nullable = false)
    private String breederNameWithoutCorp;

//    @Column(length = 70, nullable = false)
//    private String breederNameHalfKana;
//
//    @Column(length = 168, nullable = false)
//    private String breederNameEng;

    @Column(length = 20, nullable = false)
    private String breederHomeAffairName;


    @Override
    public Object getPrimaryKey() {
        return this.breederCd;
    }
}
