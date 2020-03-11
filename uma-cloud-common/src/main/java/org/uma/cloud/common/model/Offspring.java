package org.uma.cloud.common.model;

import lombok.Getter;
import org.uma.cloud.common.code.BreedCode;
import org.uma.cloud.common.code.HairColorCode;
import org.uma.cloud.common.code.SexCode;
import org.uma.cloud.common.recordSpec.RecordSpec;

import java.time.LocalDate;
import java.util.List;

/**
 * {@link RecordSpec.SK}
 */
@Getter
public class Offspring extends BaseModel {

    /**
     * 血統登録番号 10桁
     * {@link HorseRacingDetails.bloodlineNo}
     * {@link BreedingHorse.bloodlineNo}
     */
    private Long bloodlineNo;

    private LocalDate birthDate;
    private SexCode sexCd;
    private BreedCode breedCd;
    private HairColorCode hairColorCd;
    private Integer sonBringingDiv;
    private Integer importYear;
    private Integer breederCd;
    private String sourceName;

    /**
     * ３代血統 繁殖登録番号
     * <p>
     * 父･母･
     * 父父･父母･母父･母母･
     * 父父父･父父母･父母父･父母母･母父父･母父母･母母父･母母母
     * の順
     * {@link BreedingHorse.breedingNo}
     */
    private List<Integer> breeding3rd;

}
