package org.uma.platform.common.model;

import lombok.Data;
import org.uma.platform.common.code.BreedCode;
import org.uma.platform.common.code.HairColorCode;
import org.uma.platform.common.code.SexCode;
import org.uma.platform.common.config.spec.RecordSpec;

import java.time.LocalDate;

/**
 * {@link RecordSpec.HN}
 */

@Data
public class BreedingHorse {

    private RecordSpec recordType;
    private String dataDiv;
    private LocalDate dataCreateDate;

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
