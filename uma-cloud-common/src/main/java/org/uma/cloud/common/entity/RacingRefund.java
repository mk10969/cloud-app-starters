package org.uma.cloud.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.uma.cloud.common.utils.javatuples.Pair;
import org.uma.cloud.common.utils.javatuples.Triplet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
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

    @Column(nullable = false)
    private Boolean restoreFlagWin;

    @Column(nullable = false)
    private Boolean restoreFlagShow;

    @Column(nullable = false)
    private Boolean restoreFlagBracketQ;

    @Column(nullable = false)
    private Boolean restoreFlagQuinella;

    @Column(nullable = false)
    private Boolean restoreFlagQuinellaPlace;

    @Column(nullable = false)
    private Boolean restoreFlagExacta;

    @Column(nullable = false)
    private Boolean restoreFlagTrio;

    @Column(nullable = false)
    private Boolean restoreFlagTrifecta;

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


    @Data
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

    @Data
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

    @Data
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
