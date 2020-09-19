package org.uma.cloud.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@IdClass(RacingRefund.CompositeId.class)
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
     * オッズ式別符号
     * 1: 単勝
     * 2: 複勝
     * 3: 枠連
     * 4: 馬連
     * 5: ワイド
     * 6: 馬単
     * 7: 三連複
     * 8: 三連単
     */
    @Id
    @Column(nullable = false)
    private Integer betting;

    /**
     * 返還フラグ
     */
    @Column(nullable = false)
    private Boolean restoreFlag;

    /**
     * 返還馬番
     */
    @Type(type = "list")
    @Column(columnDefinition = "integer[]", nullable = false)
    private List<Integer> restoreHorseNoItems;

    /**
     * 返還枠番
     */
    @Type(type = "list")
    @Column(columnDefinition = "integer[]", nullable = false)
    private List<Integer> restoreBracketItems;

    /**
     * 返還同枠
     */
    @Type(type = "list")
    @Column(columnDefinition = "integer[]", nullable = false)
    private List<Integer> restoreSameBracketItems;

    /**
     * 払戻
     */
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<refund> refundTable;


    @Data
    public static class refund {
        /**
         * 馬番は、String型を使い、「-」セパレートにする。
         * ex...
         * 01-02-05
         */
        private String pairHorseNo;

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
    public static class CompositeId implements Serializable {

        private String raceId;

        private Integer betting;
    }

}
