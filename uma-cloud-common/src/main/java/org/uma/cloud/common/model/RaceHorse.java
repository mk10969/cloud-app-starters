package org.uma.cloud.common.model;

import lombok.Getter;
import org.hibernate.annotations.Type;
import org.uma.cloud.common.code.BreedCode;
import org.uma.cloud.common.code.EastOrWestBelongCode;
import org.uma.cloud.common.code.HairColorCode;
import org.uma.cloud.common.code.HorseSignCode;
import org.uma.cloud.common.code.SexCode;
import org.uma.cloud.common.recordSpec.RecordSpec;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

/**
 * {@link RecordSpec.UM}
 */
@Getter
@Entity
@Table(name = "uma_race_horse")
public class RaceHorse extends BaseModel {

    /**
     * 血統登録番号 10桁
     */
    @Id
    private Long bloodlineNo;

    private Boolean isRaceHorseErase;

    private LocalDate raceHorseEntryDate;

    private LocalDate raceHorseEraseDate;

    private LocalDate birthDate;

    @Column(length = 36)
    private String horseName;

    @Column(length = 36)
    private String horseNameHalfKana;

    @Column(length = 60)
    private String horseNameEng;

    private Boolean isJraFacilityStay;

    @Column(length = 19)
    private String spare1;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private HorseSignCode horseSignCd;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private SexCode sexCd;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private BreedCode breedCd;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private HairColorCode hairColorCd;

    /**
     * 父･母･
     * 父父･父母･母父･母母･
     * 父父父･父父母･父母父･父母母･母父父･母父母･母母父･母母母の順
     */
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Breeding> breeding3rd;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private EastOrWestBelongCode ewBelongCd;

    private Integer trainerCd;

    @Column(length = 8)
    private String trainerNameShort;

    @Column(length = 20)
    private String invitationAreaName;

    private Integer breederCd;

    @Column(length = 70)
    private String breederNameWithoutCorp;

    @Column(length = 20)
    private String sourceAreaName;

    private Integer ownerCd;

    @Column(length = 64)
    private String ownerNameWithoutCorp;

    private Integer addedMoneyTotalFlat;

    private Integer addedMoneyTotalJump;

    private Integer stakesMoneyTotalFlat;

    private Integer stakesMoneyTotalJump;

    private Integer allMoneyTotalFlat;

    private Integer allMoneyTotalJump;


    @Getter
    public static class Breeding {
        /**
         * 繁殖登録番号 8桁
         * {@link BreedingHorse.breedingNo}
         */
        private Integer breedingNo;

        private String horseName;

    }

}
