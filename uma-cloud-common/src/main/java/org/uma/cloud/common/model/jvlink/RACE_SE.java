package org.uma.cloud.common.model.jvlink;

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
import org.uma.cloud.common.model.JvLinkBase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
public class RACE_SE extends JvLinkBase {
    private String dataDiv;
    private String raceId;
    private LocalDate holdingDate;
    private RaceCourseCode courseCd;
    private Integer holdingNo;
    private Integer holdingDay;
    private Integer raceNo;
    private Integer bracketNo;
    private String horseNo;
    private Long bloodlineNo;
    private String horseName;
    private HorseSignCode horseSignCd;
    private SexCode sexCd;
    private BreedCode bredCd;
    private HairColorCode hairColorCd;
    private Integer age;
    private EastOrWestBelongCode ewBelongCd;
    private Integer trainerCd;
    private String trainerNameShort;
    private Integer ownerCd;
    private String ownerNameWithoutCorp;
    private String clothingMark;
    private String spare1;
    private Double loadWeight;
    private Integer loadWeightBefore;
    private Boolean isBlinker;
    private String spare2;
    private Integer jockeyCd;
    private Integer jockeyCdBefore;
    private String jockeyNameShort;
    private String jockeyNameShortBefore;
    private JockeyApprenticeCode jockeyApprenticeCd;
    private JockeyApprenticeCode jockeyApprenticeCdBefore;
    private Integer horseWeight;
    private String changeSign;
    private Integer changeAmount;
    private AbnormalDivisionCode abnormalDivCd;
    private Integer finishedArrivalOrder;
    private Integer fixedArrivalOrder;
    private Integer deadHeadDiv;
    private Integer deadHeadCount;
    private LocalTime runningTime;
    private MarginCode marginCd;
    private MarginCode marginCd2;
    private MarginCode marginCd3;
    private Integer rankCorner1;
    private Integer rankCorner2;
    private Integer rankCorner3;
    private Integer rankCorner4;
    private BigDecimal oddsWin;
    private Integer betRankWin;
    private Integer acquirementAddedMoney;
    private Integer acquirementStakesMoney;
    private String spare4;
    private String spare5;
    private Double lastFurlong4;
    private Double lastFurlong3;
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
        private Long bloodlineNo;
        private String horseName;
    }
}