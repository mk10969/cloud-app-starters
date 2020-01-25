package org.uma.platform.common.model.odds;

import lombok.Data;
import org.uma.platform.common.code.RaceCourseCode;
import org.uma.platform.common.config.spec.RecordSpec;
import org.uma.platform.common.model.RacingDetails;

import java.time.LocalDate;
import java.util.List;

/**
 * {@link RecordSpec.O1}
 */

@Data
public class WinsPlaceBracketQuinella {

    private RecordSpec recordType;
    private String dataDiv;
    private LocalDate dataCreateDate;

    /**
     * Unique
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
    private Integer saleFlagWin;
    private Integer saleFlagShow;
    private Integer saleFlagbracket;
    private Integer placeCashKey;

    // 単勝オッズ
    private List<WinOdds> winOdds;

    // 複勝オッズ
    private List<PlaceOdds> placeOdds;

    // 枠連オッズ
    private List<BracketQuinellaOdds> bracketQuinellaOdds;


    private Long voteCountTotalWin;
    private Long voteCountTotalPlace;
    private Long voteCountTotalBracketQuinella;


    @Data
    public static class WinOdds {
        /**
         * 馬番は、String型を使う。
         */
        private String horseNo;
        private Integer odds;
        private Integer betRank;
    }

    @Data
    public static class PlaceOdds {
        /**
         * 馬番は、String型を使う。
         */
        private String horseNo;
        private Integer oddsMin;
        private Integer oddsMax;
        private Integer betRank;
    }

    @Data
    public static class BracketQuinellaOdds {
        /**
         * 枠連は、Pairオブジェクトを利用するのでが正しいが、
         * どうせ使わないので、String型にしておく。
         */
        private String pairNo;
        private Integer odds;
        private Integer betRank;
    }

}
