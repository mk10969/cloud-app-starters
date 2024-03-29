package org.uma.cloud.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.code.RaceGradeCode;
import org.uma.cloud.common.code.RaceSignCode;
import org.uma.cloud.common.code.RaceTypeCode;
import org.uma.cloud.common.code.TrackCode;
import org.uma.cloud.common.code.TurfOrDirtConditionCode;
import org.uma.cloud.common.code.WeatherCode;
import org.uma.cloud.common.code.WeightTypeCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class RacingDetail extends BaseModel {

    /**
     * データ区分
     */
    @Column(length = 1, nullable = false)
    private String dataDiv;

    /**
     * {@link RacingHorseDetail.raceId}
     * {@link RacingRefund.raceId}
     * {@link RacingVote.raceId}
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

    @Column(length = 60, nullable = false)
    private String raceNameFull;

    @Column(length = 10, nullable = false)
    private RaceGradeCode gradeCd;

    @Column(length = 4, nullable = false)
    private RaceTypeCode raceTypeCd;

    @Column(length = 20, nullable = false)
    private RaceSignCode raceSignCd;

    @Column(length = 3, nullable = false)
    private WeightTypeCode weightTypeCd;

    /**
     * 2007.競走条件コード
     * は、特定のCodeクラスの型にせず
     * <p>
     * raceConditionCdOld2
     * raceConditionCdOld3
     * raceConditionCdOld4
     * raceConditionCdOld5
     * raceConditionCdYoungest
     * <p>
     * List<Integer>として格納する。
     */
    @Type(type = "list")
    @Column(columnDefinition = "integer[]", nullable = false)
    private List<Integer> raceConditions;

    /**
     * 地方競馬のみ設定
     */
    @Column(length = 60, nullable = false)
    private String raceConditionName;

    @Column(nullable = false)
    private Integer distance;

    @Column(length = 8, nullable = false)
    private TrackCode trackCd;

    @Column(length = 2, nullable = false)
    private String courseDiv;

    @Type(type = "list")
    @Column(columnDefinition = "integer[]", nullable = false)
    private List<Integer> addedMoneyItems;

    @Type(type = "list")
    @Column(columnDefinition = "integer[]", nullable = false)
    private List<Integer> stakesMoneyItems;

    // 時間 -> LocalTime
    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private Integer starterCount;

    @Column(nullable = false)
    private Integer finishedCount;

    @Column(length = 2, nullable = false)
    private WeatherCode weatherCd;

    @Column(length = 2, nullable = false)
    private TurfOrDirtConditionCode turfOrDirtCondition;

    @Type(type = "list")
    @Column(columnDefinition = "double precision[]", nullable = false)
    private List<Double> lapTimeItems;

    @Column(nullable = false)
    private Double jumpMileTime;

    @Column(nullable = false)
    private Double firstFurlong3;

    @Column(nullable = false)
    private Double firstFurlong4;

    @Column(nullable = false)
    private Double lastFurlong3;

    @Column(nullable = false)
    private Double lastFurlong4;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<CornerPassageRank> cornerPassageRanks;


    @Override
    public Object getPrimaryKey() {
        return this.raceId;
    }


    @Data
    public static class CornerPassageRank {

        private Integer corner;

        private Integer aroundCount;

        private String passageRank;

    }

}
