package org.uma.cloud.common.model.entity;

import lombok.Getter;
import org.hibernate.annotations.Type;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.utils.javatuples.Triplet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Entity
public class OddsTrio extends BaseModel implements TimeSeries {

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
    private Integer saleFlag;

    // 三連複オッズ
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<TrioOdds> trioOdds;

    @Column(nullable = false)
    private Long voteTotalCount;


    @Getter
    public static class TrioOdds {
        /**
         * 馬番の組み合わせ
         */
        private Triplet<String, String, String> pairNo;

        private BigDecimal odds;

        private Integer betRank;

    }

}
