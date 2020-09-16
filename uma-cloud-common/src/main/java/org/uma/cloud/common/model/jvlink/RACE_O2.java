package org.uma.cloud.common.model.jvlink;

import lombok.Getter;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.model.JvLinkBase;
import org.uma.cloud.common.utils.javatuples.Pair;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
public class RACE_O2 extends JvLinkBase {
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
    private List<QuinellaOdds> quinellaOdds;
    private Long voteCountTotal;

    @Getter
    public static class QuinellaOdds {
        private Pair<String, String> pairNo;
        private BigDecimal odds;
        private Integer betRank;
    }
}