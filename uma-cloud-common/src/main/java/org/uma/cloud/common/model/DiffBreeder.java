package org.uma.cloud.common.model;

import lombok.Getter;
import org.uma.cloud.common.code.RecordSpec;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * {@link RecordSpec.BR}
 */
@Getter
@Entity
@Table
// TODO: DiffBreaderに変更する。他のやつもw
public class DiffBreeder extends BaseModel {

    /**
     * データ区分
     */
    @Column(length = 1)
    private String dataDiv;

    /**
     * 生産者コード 6桁
     */
    @Id
    private Integer breederCd;

    @Column(length = 70)
    private String breederNameWithCorp;

    @Column(length = 70)
    private String breederNameWithoutCorp;

    @Column(length = 70)
    private String breederNameHalfKana;

    @Column(length = 168)
    private String breederNameEng;

    @Column(length = 20)
    private String breederHomeAffairName;

}
