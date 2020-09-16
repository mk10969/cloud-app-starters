package org.uma.cloud.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.uma.cloud.common.utils.constants.TimeSeries;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
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

    @Column(nullable = false)
    private LocalDate holdingDate;

    @Column(length = 8, nullable = false)
    private String announceDate;

    @Column(nullable = false)
    private Integer entryCount;

    @Column(nullable = false)
    private Integer starterCount;

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

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<Odds> oddsTable;

    @Column(nullable = false)
    private Long voteCountTotal;

    @Data
    public static class Odds {
        /**
         * 馬番は、String型を使い、:セパレートにする。
         * ex...
         * 01:02:05
         */
        private String pairHorseNo;

        private BigDecimal odds;

        private Integer betRank;
    }
}
