package org.uma.cloud.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.uma.cloud.common.code.BreedCode;
import org.uma.cloud.common.code.HairColorCode;
import org.uma.cloud.common.code.SexCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class BloodBreeding extends BaseModel {

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

    /**
     * 血統登録番号 10桁
     * {@link BloodLine.bloodlineNo}
     */
    @Column(nullable = false)
    private Long bloodlineNo;

    @Column(length = 36, nullable = false)
    private String horseName;

    @Column(length = 40, nullable = false)
    private String horseNameHalfKana;

    @Column(length = 80, nullable = false)
    private String horseNameEng;

    @Column(nullable = false)
    private Integer birthYear;

    @Column(length = 2, nullable = false)
    private SexCode sexCd;

    @Column(length = 8, nullable = false)
    private BreedCode breedCd;

    @Column(length = 3, nullable = false)
    private HairColorCode hairColorCd;

    @Column(nullable = false)
    private Integer breedingHorseBringingDiv;

    @Column(nullable = false)
    private Integer importYear;

    @Column(length = 20, nullable = false)
    private String sourceName;

    /**
     * 繁殖登録番号 8桁
     */
    @Column(nullable = false)
    private Integer breedingNoFather;

    /**
     * 繁殖登録番号 8桁
     */
    @Column(nullable = false)
    private Integer breedingNoMother;


    @Override
    public Object getPrimaryKey() {
        return this.breedingNo;
    }
}
