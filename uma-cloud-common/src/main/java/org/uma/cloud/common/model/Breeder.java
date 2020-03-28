package org.uma.cloud.common.model;

import lombok.Getter;
import org.uma.cloud.common.recordSpec.RecordSpec;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * {@link RecordSpec.BR}
 */
@Getter
@Entity
@Table(name = "uma_breeder")
public class Breeder extends BaseModel {

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
