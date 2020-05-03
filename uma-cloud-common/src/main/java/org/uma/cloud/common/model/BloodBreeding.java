package org.uma.cloud.common.model;

import lombok.Getter;
import org.uma.cloud.common.code.BreedCode;
import org.uma.cloud.common.code.HairColorCode;
import org.uma.cloud.common.code.RecordSpec;
import org.uma.cloud.common.code.SexCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * {@link RecordSpec.HN}
 */
@Getter
@Entity
@Table
public class BloodBreeding extends BaseModel {

    /**
     * データ区分
     */
    @Column(length = 1)
    private String dataDiv;

    /**
     * 繁殖登録番号 8桁
     */
    @Id
    private Integer breedingNo;

    @Column(length = 8)
    private String spare1;

    /**
     * 血統登録番号 10桁
     * {@link BloodLine.bloodlineNo}
     */
    private Long bloodlineNo;

    @Column(length = 1)
    private String spare2;

    @Column(length = 36)
    private String horseName;

    @Column(length = 40)
    private String horseNameHalfKana;

    @Column(length = 80)
    private String horseNameEng;

    private Integer birthYear;

    @Column(length = 2)
    private SexCode sexCd;

    @Column(length = 8)
    private BreedCode breedCd;

    @Column(length = 3)
    private HairColorCode hairColorCd;

    private Integer breedingHorseBringingDiv;

    private Integer importYear;

    @Column(length = 20)
    private String sourceName;

    /**
     * 繁殖登録番号 8桁
     */
    private Integer breedingNoFather;

    /**
     * 繁殖登録番号 8桁
     */
    private Integer breedingNoMother;

}
