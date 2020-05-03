package org.uma.cloud.common.model;

import lombok.Getter;
import org.hibernate.annotations.Type;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.code.RecordSpec;
import org.uma.cloud.common.utils.javatuples.Pair;
import org.uma.cloud.common.utils.javatuples.Triplet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

/**
 * {@link RecordSpec.HR}
 */
@Getter
@Entity
@Table
public class RacingRefund extends BaseModel {

    /**
     * データ区分
     */
    @Column(length = 1)
    private String dataDiv;

    /**
     * {@link RacingDetail.raceId}
     */
    @Id
    @Column(length = 16)
    private String raceId;

    private LocalDate holdingDate;

    @Column(length = 6)
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

    @Column(length = 1)
    private String spare1;

    private Integer failureFlagExacta;

    private Integer failureFlagTrio;

    private Integer failureFlagTrifecta;

    private Integer specialRefundFlagWin;

    private Integer specialRefundFlagShow;

    private Integer specialRefundFlagBracketQ;

    private Integer specialRefundFlagQuinella;

    private Integer specialRefundFlagQuinellaPlace;

    @Column(length = 1)
    private String spare2;

    private Integer specialRefundFlagExacta;

    private Integer specialRefundFlagTrio;

    private Integer specialRefundFlagTrifecta;

    private Integer restoreFlagWin;

    private Integer restoreFlagShow;

    private Integer restoreFlagBracketQ;

    private Integer restoreFlagQuinella;

    private Integer restoreFlagQuinellaPlace;

    @Column(length = 1)
    private String spare3;

    private Integer restoreFlagExacta;

    private Integer restoreFlagTrio;

    private Integer restoreFlagTrifecta;

    @Type(type = "list")
    @Column(columnDefinition = "integer[]")
    private List<Integer> restoreHorseNoItems;

    @Type(type = "list")
    @Column(columnDefinition = "integer[]")
    private List<Integer> restoreBracketItems;

    @Type(type = "list")
    @Column(columnDefinition = "integer[]")
    private List<Integer> restoreSameBracketItems;

    // 単勝
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<refund> refundWins;

    // 複勝
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<refund> refundShows;

    // 枠連
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<refund> refundBracketQs;

    // 馬連
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<refundPair> refundQuinellas;

    // ワイド
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<refundPair> refundQuinellaPlaces;

    // 予備
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<refundPair> refundSpares;

    // 馬単
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<refundPair> refundExactas;

    // 3連複
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<refundTriplet> refundTrios;

    // 3連単
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
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
