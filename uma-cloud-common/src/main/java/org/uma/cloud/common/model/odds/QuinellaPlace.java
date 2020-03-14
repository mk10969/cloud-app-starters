package org.uma.cloud.common.model.odds;

import lombok.Getter;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.model.BaseModel;
import org.uma.cloud.common.model.RacingDetails;
import org.uma.cloud.common.recordSpec.RecordSpec;
import org.uma.cloud.common.utils.javatuples.Pair;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * {@link RecordSpec.O3}
 */
@Getter
public class QuinellaPlace extends BaseModel {

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

    // ワイドオッズ
    private List<QuinellaPlaceOdds> quinellaPlaceOdds;

    private Long voteTotalCount;


    @Getter
    public static class QuinellaPlaceOdds {

        /**
         * 馬番の組み合わせ
         */
        private Pair<String, String> pairNo;
        private BigDecimal oddsMin;
        private BigDecimal oddsMax;
        private Integer betRank;
    }

}
