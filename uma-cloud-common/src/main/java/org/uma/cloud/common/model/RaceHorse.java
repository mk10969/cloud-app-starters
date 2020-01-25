package org.uma.platform.common.model;

import lombok.Data;
import org.uma.platform.common.code.*;
import org.uma.platform.common.config.spec.RecordSpec;

import java.time.LocalDate;
import java.util.List;

/**
 * {@link RecordSpec.UM}
 */

@Data
public class RaceHorse {

    private RecordSpec recordType;
    private String dataDiv;
    private LocalDate dataCreateDate;

    /**
     * 血統登録番号 10桁
     */
    private Long bloodlineNo;
    private Boolean isRaceHorseErase;
    private LocalDate raceHorseEntryDate;
    private LocalDate raceHorseEraseDate;
    private LocalDate birthDate;
    private String horseName;
    private String horseNameHalfKana;
    private String horseNameEng;
    private Boolean isJraFacilityStay;
    private String spare1;
    private HorseSignCode horseSignCd;
    private SexCode sexCd;
    private BreedCode breedCd;
    private HairColorCode hairColorCd;

    /**
     * 父･母･
     * 父父･父母･母父･母母･
     * 父父父･父父母･父母父･父母母･母父父･母父母･母母父･母母母の順
     */
    private List<Breeding> breeding3rd;
    private EastOrWestBelongCode ewBelongCd;

    private Integer trainerCd;
    private String trainerNameShort;
    private String invitationAreaName;

    private Integer breederCd;
    private String breederNameWithoutCorp;
    private String sourceAreaName;

    private Integer ownerCd;
    private String ownerNameWithoutCorp;

    private Long addedMoneyTotalFlat;
    private Long addedMoneyTotalJump;
    private Long stakesMoneyTotalFlat;
    private Long stakesMoneyTotalJump;
    private Long allMoneyTotalFlat;
    private Long allMoneyTotalJump;


    @Data
    public static class Breeding {
        /**
         * 繁殖登録番号 8桁
         * {@link BreedingHorse.breedingNo}
         */
        private Integer breedingNo;
        private String horseName;

    }

}
