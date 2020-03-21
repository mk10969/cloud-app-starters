package org.uma.cloud.common.model;

import lombok.Getter;
import org.uma.cloud.common.code.BreedCode;
import org.uma.cloud.common.code.HairColorCode;
import org.uma.cloud.common.code.SexCode;
import org.uma.cloud.common.recordSpec.RecordSpec;

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
public class BreedingHorse extends BaseModel {

    /**
     * 繁殖登録番号 8桁
     */
    @Id
    private Integer breedingNo;

    @Column(length = 8)
    private String spare1;

    /**
     * 血統登録番号 10桁
     * {@link Offspring.bloodlineNo}
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
    private SexCode sexCd;
    private BreedCode breedCd;
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
