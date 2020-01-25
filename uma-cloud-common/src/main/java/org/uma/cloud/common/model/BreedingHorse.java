package org.uma.cloud.common.model;

import lombok.Getter;
import lombok.Setter;
import org.uma.cloud.common.code.BreedCode;
import org.uma.cloud.common.code.HairColorCode;
import org.uma.cloud.common.code.SexCode;
import org.uma.cloud.common.recordSpec.RecordSpec;

/**
 * {@link RecordSpec.HN}
 */
@Getter
@Setter
public class BreedingHorse extends BaseModel {

    /**
     * 繁殖登録番号 8桁
     */
    private Integer breedingNo;
    private String spare1;

    /**
     * 血統登録番号 10桁
     * {@link Offspring.bloodlineNo}
     */
    private Long bloodlineNo;
    private String spare2;

    private String horseName;
    private String horseNameHalfKana;
    private String horseNameEng;
    private Integer birthYear;
    private SexCode sexCd;
    private BreedCode breedCd;
    private HairColorCode hairColorCd;
    private Integer breedingHorseBringingDiv;
    private Integer importYear;
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
