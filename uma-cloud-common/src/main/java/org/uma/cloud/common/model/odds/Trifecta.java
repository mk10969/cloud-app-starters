package org.uma.cloud.common.model.odds;


import lombok.Getter;
import org.hibernate.annotations.Type;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.model.BaseModel;
import org.uma.cloud.common.model.RacingDetails;
import org.uma.cloud.common.recordSpec.RecordSpec;
import org.uma.cloud.common.utils.javatuples.Triplet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * {@link RecordSpec.O6}
 */
@Getter
@Entity
@Table(name = "odds_trifecta")
public class Trifecta extends BaseModel {

    /**
     * {@link RacingDetails.raceId}
     */
    @Id
    @Column(length = 16)
    private String raceId;

    private LocalDate holdingDate;

    @Enumerated(EnumType.STRING)
    private RaceCourseCode courseCd;

    private Integer holdingNo;

    private Integer holdingDay;

    private Integer raceNo;

    @Column(length = 8)
    private String announceDate;

    private Integer entryCount;

    private Integer starterCount;

    private Integer saleFlag;

    // 三連単オッズ
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<TrifectaOdds> trifectaOdds;

    private Long voteTotalCount;


    @Getter
    public static class TrifectaOdds {
        /**
         * 馬番の組み合わせ
         */
        private Triplet<String, String, String> pairNo;

        private BigDecimal odds;

        private Integer betRank;

    }

}
