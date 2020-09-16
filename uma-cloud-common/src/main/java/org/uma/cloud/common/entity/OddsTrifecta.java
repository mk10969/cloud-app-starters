package org.uma.cloud.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.uma.cloud.common.utils.constants.TimeSeries;
import org.uma.cloud.common.utils.javatuples.Triplet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class OddsTrifecta extends BaseModel implements TimeSeries {

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

    @Column(length = 8, nullable = false)
    private String announceDate;

    @Column(nullable = false)
    private Integer entryCount;

    @Column(nullable = false)
    private Integer starterCount;

    @Column(nullable = false)
    private Integer saleFlag;

    // 三連単オッズ
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<TrifectaOdds> trifectaOdds;

    @Column(nullable = false)
    private Long voteTotalCount;


    @Data
    public static class TrifectaOdds {
        /**
         * 馬番の組み合わせ
         */
        private Triplet<String, String, String> pairNo;

        private BigDecimal odds;

        private Integer betRank;

    }

}
