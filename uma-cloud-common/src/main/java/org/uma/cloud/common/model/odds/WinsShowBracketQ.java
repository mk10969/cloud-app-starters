package org.uma.cloud.common.model.odds;

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
@Table(name = "odds_wins_show_bracketq")
public class WinsShowBracketQ extends BaseModel implements TimeSeries {

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

    @Column(length = 8)
    private String announceDate;

    private Integer entryCount;

    private Integer starterCount;

    private Integer saleFlagWin;

    private Integer saleFlagShow;

    private Integer saleFlagbracketQ;

    private Integer showCashKey;

    // 単勝オッズ
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<WinOdds> winOdds;

    // 複勝オッズ
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<ShowOdds> showOdds;

    // 枠連オッズ
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<BracketQOdds> bracketQOdds;

    private Long voteCountTotalWin;

    private Long voteCountTotalShow;

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
