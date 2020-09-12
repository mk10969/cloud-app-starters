package org.uma.cloud.common.model.jvlink;

import lombok.Getter;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.utils.javatuples.Pair;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
class RACE_O4 {
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
    private List<ExactaOdds> exactaOdds;
    private Long voteTotalCount;

    @Getter
    public static class ExactaOdds {
        private Pair<String, String> pairNo;
        private BigDecimal odds;
        private Integer betRank;
    }
}