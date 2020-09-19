package org.uma.cloud.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.uma.cloud.common.utils.constants.TimeSeries;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@IdClass(RacingOdds.CompositeId.class)
public class RacingOdds extends BaseModel implements TimeSeries {

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
     * 時系列オッズは、未対応。
     */
    @Column(nullable = false)
    private LocalDate holdingDate;

    /**
     * 時系列オッズは、未対応。
     */
    @Column(length = 8, nullable = false)
    private String announceDate;

    /**
     * 発売フラグ
     * 0: 発売なし
     * 1: 発売前取消
     * 3: 発売後取消
     * 7: 発売あり
     */
    @Column(nullable = false)
    private Integer saleFlag;

    /**
     * 複勝のみ設定される。それ以外null
     * 0: 複勝発売なし
     * 2: 2着まで払い
     * 3: 3着まで払い
     */
    @Column
    private Integer showCashKey;

    /**
     * オッズ
     */
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<Odds> oddsTable;

    /**
     * 投票合計票数
     */
    @Column(nullable = false)
    private Long voteCountTotal;


    @Data
    public static class Odds {
        /**
         * 馬番は、String型を使い、「-」セパレートにする。
         * ex...
         * 01-02-05
         */
        private String pairHorseNo;

        private BigDecimal oddsMin;

        private BigDecimal oddsMax;

        private Integer betRank;
    }


    @Data
    public static class CompositeId implements Serializable {

        private String raceId;

        private Integer betting;
    }

}
