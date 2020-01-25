package org.uma.platform.common.model;

import lombok.Data;
import org.uma.platform.common.code.RaceCourseCode;
import org.uma.platform.common.config.spec.RecordSpec;
import org.uma.platform.common.utils.javatuples.Pair;
import org.uma.platform.common.utils.javatuples.Triplet;

import java.time.LocalDate;
import java.util.List;

/**
 * {@link RecordSpec.HR}
 */

@Data
public class RaceRefund {

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
    private Integer entryCount;
    private Integer starterCount;

    private Integer failureFlagWin;
    private Integer failureFlagPlace;
    private Integer failureFlagBracketQuinella;
    private Integer failureFlagQuinella;
    private Integer failureFlagQuinellaPlace;
    private String spare1;
    private Integer failureFlagExacta;
    private Integer failureFlagTrio;
    private Integer failureFlagTrifecta;

    private Integer specialRefundFlagWin;
    private Integer specialRefundFlagPlace;
    private Integer specialRefundFlagBracketQuinella;
    private Integer specialRefundFlagQuinella;
    private Integer specialRefundFlagQuinellaPlace;
    private String spare2;
    private Integer specialRefundFlagExacta;
    private Integer specialRefundFlagTrio;
    private Integer specialRefundFlagTrifecta;

    private Integer restoreFlagWin;
    private Integer restoreFlagPlace;
    private Integer restoreFlagBracketQuinella;
    private Integer restoreFlagQuinella;
    private Integer restoreFlagQuinellaPlace;
    private String spare3;
    private Integer restoreFlagExacta;
    private Integer restoreFlagTrio;
    private Integer restoreFlagTrifecta;

    // ここは特殊
    private List<Integer> restoreHorseNoItems;
    private List<Integer> restoreBracketItems;
    private List<Integer> restoreSameBracketItems;

    // 単勝
    private List<refund> refundWins;
    // 複勝
    private List<refund> refundPlaces;
    // 枠連
    private List<refund> refundBracketQuinellas;
    // 馬連
    private List<refundPair> refundQuinellas;
    // ワイド
    private List<refundPair> refundQuinellaPlaces;
    // 予備
    private List<refundPair> refundSpares;
    // 馬単
    private List<refundPair> refundExactas;
    // 3連複
    private List<refundTriplet> refundTrios;
    // 3連単
    private List<refundTriplet> refundTrifectas;


    @Data
    public static class refund {

        /**
         * 馬番は組み合わせになるため、String型を使う。
         */
        private String horseNo;

        /**
         * 取得する初期値スペース => null に変換される。
         */
        private Long refundMoney;

        /**
         * 取得する初期値スペース => null に変換する。
         */
        private Integer betRank;
    }

    @Data
    public static class refundPair {

        /**
         * 馬番の組み合わせ
         */
        private Pair<String, String> pairNo;

        /**
         * 取得する初期値スペース => null に変換される。
         */
        private Long refundMoney;

        /**
         * 取得する初期値スペース => null に変換する。
         */
        private Integer betRank;
    }

    @Data
    public static class refundTriplet {

        /**
         * 馬番の組み合わせ
         */
        private Triplet<String, String, String> tripletNo;

        /**
         * 取得する初期値スペース => null に変換される。
         */
        private Long refundMoney;

        /**
         * 取得する初期値スペース => null に変換する。
         */
        private Integer betRank;
    }


}
