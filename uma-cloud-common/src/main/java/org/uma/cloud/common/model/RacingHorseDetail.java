package org.uma.cloud.common.model;

import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.Type;
import org.uma.cloud.common.code.AbnormalDivisionCode;
import org.uma.cloud.common.code.BreedCode;
import org.uma.cloud.common.code.EastOrWestBelongCode;
import org.uma.cloud.common.code.HairColorCode;
import org.uma.cloud.common.code.HorseSignCode;
import org.uma.cloud.common.code.JockeyApprenticeCode;
import org.uma.cloud.common.code.MarginCode;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.code.SexCode;
import org.uma.cloud.common.code.RecordSpec;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * {@link RecordSpec.SE}
 */
@Getter
@Entity
@IdClass(RacingHorseDetail.CompositeId.class)
@Table
public class RacingHorseDetail extends BaseModel {

    /**
     * {@link RacingDetail.raceId}
     */
    @Id
    @Column(length = 16)
    private String raceId;

    private LocalDate holdingDate;

    @Column(length = 6)
    private RaceCourseCode courseCd;

    private Integer holdingNo;

    private Integer holdingDay;

    /**
     * レース番号
     */
    private Integer raceNo;

    /**
     * 枠番
     */
    private Integer bracketNo;

    /**
     * 馬番
     */
    @Id
    @Column(length = 2)
    private String horseNo;

    /**
     * {@link BloodLine.bloodlineNo}
     */
    private Long bloodlineNo;

    @Column(length = 36)
    private String horseName;

    @Column(length = 9)
    private HorseSignCode horseSignCd;

    @Column(length = 2)
    private SexCode sexCd;

    @Column(length = 8)
    private BreedCode bredCd;

    @Column(length = 3)
    private HairColorCode hairColorCd;

    private Integer age;

    // 競走馬の東西
    @Column(length = 4)
    private EastOrWestBelongCode ewBelongCd;

    private Integer trainerCd;

    @Column(length = 8)
    private String trainerNameShort;

    private Integer ownerCd;

    @Column(length = 64)
    private String ownerNameWithoutCorp;

    @Column(length = 60)
    private String clothingMark;

    @Column(length = 60)
    private String spare1;

    // 負担重量 0.1kg
    private Double loadWeight;

    private Integer loadWeightBefore;

    private Boolean isBlinker;

    @Column(length = 1)
    private String spare2;

    private Integer jockeyCd;

    private Integer jockeyCdBefore;

    @Column(length = 8)
    private String jockeyNameShort;

    @Column(length = 8)
    private String jockeyNameShortBefore;

    @Column(length = 4)
    private JockeyApprenticeCode jockeyApprenticeCd;

    @Column(length = 4)
    private JockeyApprenticeCode jockeyApprenticeCdBefore;

    private Integer horseWeight;

    @Column(length = 1)
    private String changeSign;

    private Integer changeAmount;

    @Column(length = 5)
    private AbnormalDivisionCode abnormalDivCd;

    private Integer finishedArrivalOrder;

    private Integer fixedArrivalOrder;

    private Integer deadHeadDiv;

    private Integer deadHeadCount;

    private LocalTime runningTime;

    @Column(length = 6)
    private MarginCode marginCd;

    @Column(length = 6)
    private MarginCode marginCd2;

    @Column(length = 6)
    private MarginCode marginCd3;

    private Integer rankCorner1;

    private Integer rankCorner2;

    private Integer rankCorner3;

    private Integer rankCorner4;

    private BigDecimal oddsWin;

    private Integer betRankWin;

    private Integer acquirementAddedMoney;

    private Integer acquirementStakesMoney;

    @Column(length = 3)
    private String spare4;

    @Column(length = 3)
    private String spare5;

    private Double lastFurlong4;

    private Double lastFurlong3;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Contender> contenders;

    // タイム差 +999 or -001 or 9999
    @Column(length = 4)
    private String timeMargin;

    private Integer recordUpdateDiv;

//    private Integer miningDiv;
//    private String miningExpectationRunningTime;
//    private String miningExpectationErrorPlus;
//    private String miningExpectationErrorMinus;
//    private Integer miningExpectationRank;

    private Integer runningStyle;

    @Getter
    public static class Contender {

        /**
         * 血統登録番号 10桁
         * {@link BloodLine.bloodlineNo}
         */
        private Long bloodlineNo;

        private String horseName;
    }

    @Data
    public static class CompositeId implements Serializable {

        private String raceId;

        private String horseNo;
    }

}
