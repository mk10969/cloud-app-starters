package org.uma.cloud.common.model;

import lombok.Getter;
import org.hibernate.annotations.Type;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.code.RecordSpec;
import org.uma.cloud.common.model.BaseModel;
import org.uma.cloud.common.model.RacingDetail;
import org.uma.cloud.common.model.TimeSeries;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * {@link RecordSpec.O1}
 */
@Getter
@Entity
public class OddsWinsShowBracketQ extends BaseModel implements TimeSeries {

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
    private LocalDate holdingDate;

    @Column(length = 6, nullable = false)
    private RaceCourseCode courseCd;

    @Column(nullable = false)
    private Integer holdingNo;

    @Column(nullable = false)
    private Integer holdingDay;

    @Column(nullable = false)
    private Integer raceNo;

    @Column(length = 8, nullable = false)
    private String announceDate;

    @Column(nullable = false)
    private Integer entryCount;

    @Column(nullable = false)
    private Integer starterCount;

    @Column(nullable = false)
    private Integer saleFlagWin;

    @Column(nullable = false)
    private Integer saleFlagShow;

    @Column(nullable = false)
    private Integer saleFlagbracketQ;

    @Column(nullable = false)
    private Integer showCashKey;

    // 単勝オッズ
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<WinOdds> winOdds;

    // 複勝オッズ
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<ShowOdds> showOdds;

    // 枠連オッズ
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<BracketQOdds> bracketQOdds;

    @Column(nullable = false)
    private Long voteCountTotalWin;

    /**
     * Nullable
     */
    private Long voteCountTotalShow;

    @Column(nullable = false)
    private Long voteCountTotalBracketQ;


    @Getter
    public static class WinOdds {
        /**
         * 馬番は、String型を使う。
         */
        private String horseNo;

        private BigDecimal odds;

        private Integer betRank;

    }

    @Getter
    public static class ShowOdds {
        /**
         * 馬番は、String型を使う。
         */
        private String horseNo;

        private BigDecimal oddsMin;

        private BigDecimal oddsMax;

        private Integer betRank;

    }

    @Getter
    public static class BracketQOdds {
        /**
         * 枠連は、Pairオブジェクトを利用するのでが正しいが、
         * どうせ使わないので、String型にしておく。
         */
        private String pairNo;

        private BigDecimal odds;

        private Integer betRank;

    }

}
