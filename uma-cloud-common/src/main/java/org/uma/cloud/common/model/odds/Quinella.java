package org.uma.platform.common.model.odds;


import lombok.Data;
import org.uma.platform.common.code.RaceCourseCode;
import org.uma.platform.common.config.spec.RecordSpec;
import org.uma.platform.common.model.RacingDetails;
import org.uma.platform.common.utils.javatuples.Pair;

import java.time.LocalDate;
import java.util.List;

/**
 * {@link RecordSpec.O2}
 */

@Data
public class Quinella {

    private RecordSpec recordType;
    private String dataDiv;
    private LocalDate dataCreateDate;

    /**
     * {@link RacingDetails.raceId}
     */
    private String raceId;
    private LocalDate holdingDate;
    private RaceCourseCode courseCd;
    private Integer holdingNo;
    private Integer holdingDay;
    private Integer raceNo;

    // いらんから除去
//    /**
//     * 時系列オッズを使用する場合のみキーとして設定
//     */
//    private LocalDate announceDate;

    private Integer entryCount;
    private Integer starterCount;
    private Integer saleFlag;

    // 馬連オッズ
    private List<QuinellaOdds> quinellaOdds;

    private Long voteCountTotal;


    @Data
    public static class QuinellaOdds {
        /**
         * 馬番の組み合わせ
         */
        private Pair<String, String> pairNo;
        private Integer odds;
        private Integer betRank;
    }

}
