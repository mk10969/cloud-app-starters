package org.uma.cloud.common.model.entity;

import lombok.Data;
import lombok.Getter;
import org.uma.cloud.common.code.AbnormalDivisionCode;
import org.uma.cloud.common.code.BreedCode;
import org.uma.cloud.common.code.EastOrWestBelongCode;
import org.uma.cloud.common.code.HairColorCode;
import org.uma.cloud.common.code.HorseSignCode;
import org.uma.cloud.common.code.JockeyApprenticeCode;
import org.uma.cloud.common.code.MarginCode;
import org.uma.cloud.common.code.SexCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalTime;

@Getter
@Entity
@IdClass(RacingHorseDetail.CompositeId.class)
public class RacingHorseDetail extends BaseModel {

    /**
     * データ区分
     */
    @Column(length = 1, nullable = false)
    private String dataDiv;

    /**
     * {@link RacingDetail.raceId}
     */
    @Id
    @Column(length = 16, nullable = false)
    private String raceId;

    /**
     * {@link RacingDetail}  重複
     */
//    @Column(nullable = false)
//    private LocalDate holdingDate;

//    @Column(length = 6, nullable = false)
//    private RaceCourseCode courseCd;

//    @Column(nullable = false)
//    private Integer holdingNo;
//
//    @Column(nullable = false)
//    private Integer holdingDay;

//    @Column(nullable = false)
//    private Integer raceNo;

    /**
     * 枠番
     */
    @Column(nullable = false)
    private Integer bracketNo;

    /**
     * 馬番
     */
    @Id
    @Column(length = 2, nullable = false)
    private String horseNo;

    /**
     * {@link BloodLine.bloodlineNo}
     */
    @Id
    @Column(nullable = false)
    private Long bloodlineNo;

    @Column(length = 36, nullable = false)
    private String horseName;

    @Column(length = 9, nullable = false)
    private HorseSignCode horseSignCd;

    @Column(length = 2, nullable = false)
    private SexCode sexCd;

    @Column(length = 8, nullable = false)
    private BreedCode bredCd;

    @Column(length = 3, nullable = false)
    private HairColorCode hairColorCd;

    @Column(nullable = false)
    private Integer age;

    // 競走馬の東西
    @Column(length = 4, nullable = false)
    private EastOrWestBelongCode ewBelongCd;

    @Column(nullable = false)
    private Integer trainerCd;

    @Column(length = 8, nullable = false)
    private String trainerNameShort;

    @Column(nullable = false)
    private Integer ownerCd;

    @Column(length = 64, nullable = false)
    private String ownerNameWithoutCorp;

    @Column(length = 60, nullable = false)
    private String clothingMark;

//    @Column(length = 60, nullable = false)
//    private String spare1;

    // 負担重量 0.1kg
    @Column(nullable = false)
    private Double loadWeight;

    @Column(nullable = false)
    private Integer loadWeightBefore;

    @Column(nullable = false)
    private Boolean isBlinker;

//    @Column(length = 1, nullable = false)
//    private String spare2;

    @Column(nullable = false)
    private Integer jockeyCd;

    @Column(nullable = false)
    private Integer jockeyCdBefore;

    @Column(length = 8, nullable = false)
    private String jockeyNameShort;

    @Column(length = 8, nullable = false)
    private String jockeyNameShortBefore;

    @Column(length = 4, nullable = false)
    private JockeyApprenticeCode jockeyApprenticeCd;

    @Column(length = 4, nullable = false)
    private JockeyApprenticeCode jockeyApprenticeCdBefore;

    /**
     * Nullable
     */
    private Integer horseWeight;

    @Column(length = 1, nullable = false)
    private String changeSign;

    /**
     * Nullable
     */
    private Integer changeAmount;

    @Column(length = 5, nullable = false)
    private AbnormalDivisionCode abnormalDivCd;

    @Column(nullable = false)
    private Integer finishedArrivalOrder;

    @Column(nullable = false)
    private Integer fixedArrivalOrder;

    @Column(nullable = false)
    private Integer deadHeadDiv;

    @Column(nullable = false)
    private Integer deadHeadCount;

    @Column(nullable = false)
    private LocalTime runningTime;

    @Column(length = 6, nullable = false)
    private MarginCode marginCd;

    @Column(length = 6, nullable = false)
    private MarginCode marginCd2;

    @Column(length = 6, nullable = false)
    private MarginCode marginCd3;

    @Column(nullable = false)
    private Integer rankCorner1;

    @Column(nullable = false)
    private Integer rankCorner2;

    @Column(nullable = false)
    private Integer rankCorner3;

    @Column(nullable = false)
    private Integer rankCorner4;

    @Column(nullable = false)
    private BigDecimal oddsWin;

    @Column(nullable = false)
    private Integer betRankWin;

    @Column(nullable = false)
    private Integer acquirementAddedMoney;

    @Column(nullable = false)
    private Integer acquirementStakesMoney;

//    @Column(length = 3, nullable = false)
//    private String spare4;
//
//    @Column(length = 3, nullable = false)
//    private String spare5;

    @Column(nullable = false)
    private Double lastFurlong4;

    @Column(nullable = false)
    private Double lastFurlong3;

//    @Type(type = "jsonb")
//    @Column(columnDefinition = "jsonb", nullable = false)
//    private List<Contender> contenders;

    // タイム差 +999 or -001 or 9999
    @Column(length = 4, nullable = false)
    private String timeMargin;

//    @Column(nullable = false)
//    private Integer recordUpdateDiv;

//    private Integer miningDiv;
//    private String miningExpectationRunningTime;
//    private String miningExpectationErrorPlus;
//    private String miningExpectationErrorMinus;
//    private Integer miningExpectationRank;

//    @Column(nullable = false)
//    private Integer runningStyle;

//    @Getter
//    public static class Contender {
//
//        /**
//         * 血統登録番号 10桁
//         * {@link BloodLine.bloodlineNo}
//         */
//        private Long bloodlineNo;
//
//        private String horseName;
//    }

    /**
     * 血統登録番号を入れておかないと、
     * 海外レースのとき、一意に識別できない。
     */
    @Data
    public static class CompositeId implements Serializable {

        private String raceId;

        private String horseNo;

        private Long bloodlineNo;
    }

}
