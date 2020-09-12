package org.uma.cloud.common.model.entity;

import lombok.Getter;
import org.hibernate.annotations.Type;
import org.uma.cloud.common.utils.javatuples.Pair;
import org.uma.cloud.common.utils.javatuples.Triplet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Getter
@Entity
public class RacingRefund extends BaseModel {

    /**
     * データ区分
     */
    @Column(length = 1, nullable = false)
    private String dataDiv;

    /**
     * {@link RacingDetail.raceId}
     */
    @Id
    @Column(length = 16, nullable = false)
    private String raceId;

    /**
     * {@link RacingDetail}  重複
     */
//    @Column(nullable = false)
//    private LocalDate holdingDate;
//
//    @Column(length = 6, nullable = false)
//    private RaceCourseCode courseCd;
//
//    @Column(nullable = false)
//    private Integer holdingNo;
//
//    @Column(nullable = false)
//    private Integer holdingDay;
//
//    @Column(nullable = false)
//    private Integer raceNo;
//
//    @Column(nullable = false)
//    private Integer entryCount;
//
//    @Column(nullable = false)
//    private Integer starterCount;

    @Column(nullable = false)
    private Integer failureFlagWin;

    @Column(nullable = false)
    private Integer failureFlagShow;

    @Column(nullable = false)
    private Integer failureFlagBracketQ;

    @Column(nullable = false)
    private Integer failureFlagQuinella;

    @Column(nullable = false)
    private Integer failureFlagQuinellaPlace;

    @Column(length = 1, nullable = false)
    private String spare1;

    @Column(nullable = false)
    private Integer failureFlagExacta;

    @Column(nullable = false)
    private Integer failureFlagTrio;

    @Column(nullable = false)
    private Integer failureFlagTrifecta;

    @Column(nullable = false)
    private Integer specialRefundFlagWin;

    @Column(nullable = false)
    private Integer specialRefundFlagShow;

    @Column(nullable = false)
    private Integer specialRefundFlagBracketQ;

    @Column(nullable = false)
    private Integer specialRefundFlagQuinella;

    @Column(nullable = false)
    private Integer specialRefundFlagQuinellaPlace;

    @Column(length = 1, nullable = false)
    private String spare2;

    @Column(nullable = false)
    private Integer specialRefundFlagExacta;

    @Column(nullable = false)
    private Integer specialRefundFlagTrio;

    @Column(nullable = false)
    private Integer specialRefundFlagTrifecta;

    @Column(nullable = false)
    private Integer restoreFlagWin;

    @Column(nullable = false)
    private Integer restoreFlagShow;

    @Column(nullable = false)
    private Integer restoreFlagBracketQ;

    @Column(nullable = false)
    private Integer restoreFlagQuinella;

    @Column(nullable = false)
    private Integer restoreFlagQuinellaPlace;

    @Column(length = 1, nullable = false)
    private String spare3;

    @Column(nullable = false)
    private Integer restoreFlagExacta;

    @Column(nullable = false)
    private Integer restoreFlagTrio;

    @Column(nullable = false)
    private Integer restoreFlagTrifecta;

    @Type(type = "list")
    @Column(columnDefinition = "integer[]", nullable = false)
    private List<Integer> restoreHorseNoItems;

    @Type(type = "list")
    @Column(columnDefinition = "integer[]", nullable = false)
    private List<Integer> restoreBracketItems;

    @Type(type = "list")
    @Column(columnDefinition = "integer[]", nullable = false)
    private List<Integer> restoreSameBracketItems;

    // 単勝
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<refund> refundWins;

    // 複勝
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<refund> refundShows;

    // 枠連
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<refund> refundBracketQs;

    // 馬連
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<refundPair> refundQuinellas;

    // ワイド
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<refundPair> refundQuinellaPlaces;

    // 予備
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<refundPair> refundSpares;

    // 馬単
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<refundPair> refundExactas;

    // 3連複
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<refundTriplet> refundTrios;

    // 3連単
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
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
