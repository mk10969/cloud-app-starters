package org.uma.cloud.common.model.jvlink;

import lombok.Getter;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.model.BaseJvLink;
import org.uma.cloud.common.utils.javatuples.Pair;
import org.uma.cloud.common.utils.javatuples.Triplet;

import java.time.LocalDate;
import java.util.List;

@Getter
public class RACE_HR extends BaseJvLink {
    private String dataDiv;
    private String raceId;
    private LocalDate holdingDate;
    private RaceCourseCode courseCd;
    private Integer holdingNo;
    private Integer holdingDay;
    private Integer raceNo;
    private Integer entryCount;
    private Integer starterCount;
    private Integer failureFlagWin;
    private Integer failureFlagShow;
    private Integer failureFlagBracketQ;
    private Integer failureFlagQuinella;
    private Integer failureFlagQuinellaPlace;
    private String spare1;
    private Integer failureFlagExacta;
    private Integer failureFlagTrio;
    private Integer failureFlagTrifecta;
    private Integer specialRefundFlagWin;
    private Integer specialRefundFlagShow;
    private Integer specialRefundFlagBracketQ;
    private Integer specialRefundFlagQuinella;
    private Integer specialRefundFlagQuinellaPlace;
    private String spare2;
    private Integer specialRefundFlagExacta;
    private Integer specialRefundFlagTrio;
    private Integer specialRefundFlagTrifecta;
    private Integer restoreFlagWin;
    private Integer restoreFlagShow;
    private Integer restoreFlagBracketQ;
    private Integer restoreFlagQuinella;
    private Integer restoreFlagQuinellaPlace;
    private String spare3;
    private Integer restoreFlagExacta;
    private Integer restoreFlagTrio;
    private Integer restoreFlagTrifecta;
    private List<Integer> restoreHorseNoItems;
    private List<Integer> restoreBracketItems;
    private List<Integer> restoreSameBracketItems;
    private List<refund> refundWins;
    private List<refund> refundShows;
    private List<refund> refundBracketQs;
    private List<refundPair> refundQuinellas;
    private List<refundPair> refundQuinellaPlaces;
    private List<refundPair> refundSpares;
    private List<refundPair> refundExactas;
    private List<refundTriplet> refundTrios;
    private List<refundTriplet> refundTrifectas;

    @Getter
    public static class refund {
        /**
         * 馬番は組み合わせになるため、String型を使う。
         */
        private String horseNo;
        /**
         * 取得する初期値スペース => null に変換する。
         */
        private Integer refundMoney;
        /**
         * 取得する初期値スペース => null に変換する。
         */
        private Integer betRank;
    }

    @Getter
    public static class refundPair {
        /**
         * 馬番の組み合わせ
         */
        private Pair<String, String> pairNo;

        /**
         * 取得する初期値スペース => null に変換する。
         */
        private Integer refundMoney;

        /**
         * 取得する初期値スペース => null に変換する。
         */
        private Integer betRank;
    }

    @Getter
    public static class refundTriplet {
        /**
         * 馬番の組み合わせ
         */
        private Triplet<String, String, String> tripletNo;
        /**
         * 取得する初期値スペース => null に変換する。
         */
        private Integer refundMoney;
        /**
         * 取得する初期値スペース => null に変換する。
         */
        private Integer betRank;
    }
}