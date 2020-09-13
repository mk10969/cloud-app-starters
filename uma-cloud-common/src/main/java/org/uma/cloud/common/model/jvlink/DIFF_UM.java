package org.uma.cloud.common.model.jvlink;

import lombok.Getter;
import org.uma.cloud.common.code.BreedCode;
import org.uma.cloud.common.code.EastOrWestBelongCode;
import org.uma.cloud.common.code.HairColorCode;
import org.uma.cloud.common.code.HorseSignCode;
import org.uma.cloud.common.code.SexCode;
import org.uma.cloud.common.model.BaseJvLink;

import java.time.LocalDate;
import java.util.List;

@Getter
public class DIFF_UM extends BaseJvLink {
    private String dataDiv;
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
    private Integer addedMoneyTotalFlat;
    private Integer addedMoneyTotalJump;
    private Integer stakesMoneyTotalFlat;
    private Integer stakesMoneyTotalJump;
    private Integer allMoneyTotalFlat;
    private Integer allMoneyTotalJump;

    @Getter
    public static class Breeding {
        private Integer breedingNo;
        private String horseName;
    }
}