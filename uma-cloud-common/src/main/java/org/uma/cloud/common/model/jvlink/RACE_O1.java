package org.uma.cloud.common.model.jvlink;

import lombok.Getter;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.model.BaseJvLink;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
public class RACE_O1 extends BaseJvLink {
    private String dataDiv;
    private String raceId;
    private LocalDate holdingDate;
    private RaceCourseCode courseCd;
    private Integer holdingNo;
    private Integer holdingDay;
    private Integer raceNo;
    private String announceDate;
    private Integer entryCount;
    private Integer starterCount;
    private Integer saleFlagWin;
    private Integer saleFlagShow;
    private Integer saleFlagbracketQ;
    private Integer showCashKey;
    private List<WinOdds> winOdds;
    private List<ShowOdds> showOdds;
    private List<BracketQOdds> bracketQOdds;
    private Long voteCountTotalWin;
    private Long voteCountTotalShow;
    private Long voteCountTotalBracketQ;

    @Getter
    public static class WinOdds {
        private String horseNo;
        private BigDecimal odds;
        private Integer betRank;
    }

    @Getter
    public static class ShowOdds {
        private String horseNo;
        private BigDecimal oddsMin;
        private BigDecimal oddsMax;
        private Integer betRank;
    }

    @Getter
    public static class BracketQOdds {
        private String pairNo;
        private BigDecimal odds;
        private Integer betRank;
    }
}