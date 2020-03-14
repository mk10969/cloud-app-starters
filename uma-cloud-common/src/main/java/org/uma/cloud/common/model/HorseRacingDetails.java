package org.uma.cloud.common.model;

import lombok.Getter;
import org.uma.cloud.common.code.AbnormalDivisionCode;
import org.uma.cloud.common.code.BreedCode;
import org.uma.cloud.common.code.EastOrWestBelongCode;
import org.uma.cloud.common.code.HairColorCode;
import org.uma.cloud.common.code.HorseSignCode;
import org.uma.cloud.common.code.JockeyApprenticeCode;
import org.uma.cloud.common.code.MarginCode;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.code.SexCode;
import org.uma.cloud.common.recordSpec.RecordSpec;

import java.time.LocalDate;
import java.util.List;

/**
 * {@link RecordSpec.SE}
 */
@Getter
public class HorseRacingDetails extends BaseModel {

    /**
     * No unique
     * {@link RacingDetails.raceId}
     */
    private String raceId;
    private LocalDate holdingDate;
    private RaceCourseCode courseCd;
    private Integer holdingNo;
    private Integer holdingDay;
    private Integer raceNo;
    private Integer bracketNo;

    /**
     * 馬番は、払戻、票数のフィールドと合わせて、String型とする。
     * 01, 02...とデータが、保持されてほしいため。
     */
    private String horseNo;

    /**
     * {@link Offspring.bloodlineNo}
     */
    private Long bloodlineNo;
    private String horseName;
    private HorseSignCode horseSignCd;
    private SexCode sexCd;
    private BreedCode bredCd;
    private HairColorCode hairColorCd;
    private Integer age;
    // 競走馬の東西
    private EastOrWestBelongCode ewBelongCd;
    private Integer trainerCd;
    private String trainerNameShort;
    private Integer ownerCd;
    private String ownerNameWithoutCorp;
    private String clothingMark;
    private String spare1;
    private Float loadWeight;
    private Float loadWeightBefore;
    private Integer blinkerUseDiv;
    private String spare2;
    private Integer jockeyCd;
    private String jockeyCdBefore;
    private String jockeyNameShort;
    private String jockeyNameShortBefore;
    private JockeyApprenticeCode jockeyApprenticeCd;
    private JockeyApprenticeCode jockeyApprenticeCdBefore;
    private Integer horseWeight;
    private Character changeSign;
    private Integer changeAmount;
    private AbnormalDivisionCode abnormalDivCd;
    private Integer finishedArrivalOrder;
    private Integer fixedArrivalOrder;
    private Integer deadHeadDiv;
    private Integer deadHeadCount;
    private String runningTime;
    private MarginCode marginCd;
    private MarginCode marginCd2;
    private MarginCode marginCd3;
    private Integer rankCorner1;
    private Integer rankCorner2;
    private Integer rankCorner3;
    private Integer rankCorner4;
    private String oddsWin;
    private Integer betRankWin;
    private Integer acquirementAddedMoney;
    private Integer acquirementStakesMoney;
    private String spare4;
    private String spare5;
    private Float lastFurlong4;
    private Float lastFurlong3;
    private List<Contender> contenders;
    private String timeMargin;
    private Integer recordUpdateDiv;
    private Integer miningDiv;
    private String miningExpectationRunningTime;
    private String miningExpectationErrorPlus;
    private String miningExpectationErrorMinus;
    private Integer miningExpectationRank;
    private Integer runningStyle;

    @Getter
    public static class Contender {

        /**
         * 血統登録番号 10桁
         * {@link Offspring.bloodlineNo}
         */
        private Long bloodlineNo;
        private String horseName;
    }

}
