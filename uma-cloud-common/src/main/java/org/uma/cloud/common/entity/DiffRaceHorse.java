package org.uma.cloud.common.entity;

import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.Type;
import org.uma.cloud.common.code.BreedCode;
import org.uma.cloud.common.code.EastOrWestBelongCode;
import org.uma.cloud.common.code.HairColorCode;
import org.uma.cloud.common.code.HorseSignCode;
import org.uma.cloud.common.code.SexCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Entity
@IdClass(DiffRaceHorse.CompositeId.class)
public class DiffRaceHorse extends BaseModel {

    /**
     * データ区分
     */
    @Id
    @Column(length = 1, nullable = false)
    private String dataDiv;

    /**
     * 血統登録番号 10桁
     */
    @Id
    @Column(nullable = false)
    private Long bloodlineNo;

    @Column(nullable = false)
    private Boolean isRaceHorseErase;

    /**
     * Nullable
     */
    private LocalDate raceHorseEntryDate;

    /**
     * Nullable
     */
    private LocalDate raceHorseEraseDate;

    /**
     * Nullable
     */
    private LocalDate birthDate;

    @Column(length = 36, nullable = false)
    private String horseName;

    @Column(length = 36, nullable = false)
    private String horseNameHalfKana;

    @Column(length = 60, nullable = false)
    private String horseNameEng;

    @Column(nullable = false)
    private Boolean isJraFacilityStay;

    @Column(length = 19, nullable = false)
    private String spare1;

    @Column(length = 9, nullable = false)
    private HorseSignCode horseSignCd;

    @Column(length = 2, nullable = false)
    private SexCode sexCd;

    @Column(length = 8, nullable = false)
    private BreedCode breedCd;

    @Column(length = 3, nullable = false)
    private HairColorCode hairColorCd;

    /**
     * 父･母･
     * 父父･父母･母父･母母･
     * 父父父･父父母･父母父･父母母･母父父･母父母･母母父･母母母の順
     */
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<Breeding> breeding3rd;

    @Column(length = 4, nullable = false)
    private EastOrWestBelongCode ewBelongCd;

    @Column(nullable = false)
    private Integer trainerCd;

    @Column(length = 8, nullable = false)
    private String trainerNameShort;

    @Column(length = 20, nullable = false)
    private String invitationAreaName;

    @Column(nullable = false)
    private Integer breederCd;

    @Column(length = 70, nullable = false)
    private String breederNameWithoutCorp;

    @Column(length = 20, nullable = false)
    private String sourceAreaName;

    @Column(nullable = false)
    private Integer ownerCd;

    @Column(length = 64, nullable = false)
    private String ownerNameWithoutCorp;

    @Column(nullable = false)
    private Integer addedMoneyTotalFlat;

    @Column(nullable = false)
    private Integer addedMoneyTotalJump;

    @Column(nullable = false)
    private Integer stakesMoneyTotalFlat;

    @Column(nullable = false)
    private Integer stakesMoneyTotalJump;

    @Column(nullable = false)
    private Integer allMoneyTotalFlat;

    @Column(nullable = false)
    private Integer allMoneyTotalJump;


    @Getter
    public static class Breeding {
        /**
         * 繁殖登録番号 8桁
         * {@link BloodBreeding.breedingNo}
         */
        private Integer breedingNo;

        private String horseName;

    }

    @Data
    public static class CompositeId implements Serializable {

        private String dataDiv;

        private Long bloodlineNo;
    }

}
