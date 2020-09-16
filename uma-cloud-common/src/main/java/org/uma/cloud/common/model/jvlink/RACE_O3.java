package org.uma.cloud.common.model.jvlink;

import lombok.Getter;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.model.JvLinkBase;
import org.uma.cloud.common.utils.javatuples.Pair;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
public class RACE_O3 extends JvLinkBase {
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
    private Integer saleFlag;
    private List<QuinellaPlaceOdds> quinellaPlaceOdds;
    private Long voteCountTotal;

    @Getter
    public static class QuinellaPlaceOdds {
        private Pair<String, String> pairNo;
        private BigDecimal oddsMin;
        private BigDecimal oddsMax;
        private Integer betRank;
    }
}