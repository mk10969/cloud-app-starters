package org.uma.cloud.common.model.odds;

import lombok.Getter;
import org.hibernate.annotations.Type;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.model.BaseModel;
import org.uma.cloud.common.model.RacingDetails;
import org.uma.cloud.common.recordSpec.RecordSpec;

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
@Table
public class WinsPlaceBracketQuinella extends BaseModel {

    /**
     * {@link RacingDetails.raceId}
     */
    @Id
    @Column(length = 16)
    private String raceId;

    private LocalDate holdingDate;
    private RaceCourseCode courseCd;
    private Integer holdingNo;
    private Integer holdingDay;
    private Integer raceNo;

    @Column(length = 8)
    private String announceDate;

    private Integer entryCount;
    private Integer starterCount;
    private Integer saleFlagWin;
    private Integer saleFlagPlace;
    private Integer saleFlagbracket;
    private Integer placeCashKey;

    // 単勝オッズ
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<WinOdds> winOdds;

    // 複勝オッズ
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<PlaceOdds> placeOdds;

    // 枠連オッズ
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<BracketQuinellaOdds> bracketQuinellaOdds;


    private Long voteCountTotalWin;
    private Long voteCountTotalPlace;
    private Long voteCountTotalBracketQuinella;


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
    public static class PlaceOdds {
        /**
         * 馬番は、String型を使う。
         */
        private String horseNo;
        private BigDecimal oddsMin;
        private BigDecimal oddsMax;
        private Integer betRank;
    }

    @Getter
    public static class BracketQuinellaOdds {
        /**
         * 枠連は、Pairオブジェクトを利用するのでが正しいが、
         * どうせ使わないので、String型にしておく。
         */
        private String pairNo;
        private BigDecimal odds;
        private Integer betRank;
    }

}
