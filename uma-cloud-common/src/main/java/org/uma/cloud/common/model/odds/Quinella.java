package org.uma.cloud.common.model.odds;


import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.Type;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.model.BaseModel;
import org.uma.cloud.common.model.RacingDetails;
import org.uma.cloud.common.recordSpec.RecordSpec;
import org.uma.cloud.common.utils.javatuples.Pair;

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
 * {@link RecordSpec.O2}
 */
@Getter
@Entity
@Table(name = "odds_quinella")
public class Quinella extends BaseModel {

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

    // 馬連オッズ
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<QuinellaOdds> quinellaOdds;

    private Long voteCountTotal;


    @Data
    public static class QuinellaOdds {
        /**
         * 馬番の組み合わせ
         */
        private Pair<String, String> pairNo;

        private BigDecimal odds;

        private Integer betRank;

    }

}
