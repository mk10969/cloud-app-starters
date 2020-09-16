package org.uma.cloud.common.model.jvlink;

import lombok.Getter;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.model.JvLinkBase;
import org.uma.cloud.common.utils.javatuples.Pair;
import org.uma.cloud.common.utils.javatuples.Triplet;

import java.time.LocalDate;
import java.util.List;

@Getter
public class RACE_HR extends JvLinkBase {
    private String dataDiv;
    private String raceId;
    private LocalDate holdingDate;
    private RaceCourseCode courseCd;
    private Integer holdingNo;
    private Integer holdingDay;
    private Integer raceNo;
    private Integer entryCount;
    private Integer starterCount;
    private Boolean failureFlagWin;
    private Boolean failureFlagShow;
    private Boolean failureFlagBracketQ;
    private Boolean failureFlagQuinella;
    private Boolean failureFlagQuinellaPlace;
    private String spare1;
    private Boolean failureFlagExacta;
    private Boolean failureFlagTrio;
    private Boolean failureFlagTrifecta;
    private Boolean specialRefundFlagWin;
    private Boolean specialRefundFlagShow;
    private Boolean specialRefundFlagBracketQ;
    private Boolean specialRefundFlagQuinella;
    private Boolean specialRefundFlagQuinellaPlace;
    private String spare2;
    private Boolean specialRefundFlagExacta;
    private Boolean specialRefundFlagTrio;
    private Boolean specialRefundFlagTrifecta;
    private Boolean restoreFlagWin;
    private Boolean restoreFlagShow;
    private Boolean restoreFlagBracketQ;
    private Boolean restoreFlagQuinella;
    private Boolean restoreFlagQuinellaPlace;
    private String spare3;
    private Boolean restoreFlagExacta;
    private Boolean restoreFlagTrio;
    private Boolean restoreFlagTrifecta;
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