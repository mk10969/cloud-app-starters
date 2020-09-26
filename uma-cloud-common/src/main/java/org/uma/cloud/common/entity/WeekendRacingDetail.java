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
public class WeekendRacingDetail extends BaseModel {

    @Column(length = 1, nullable = false)
    private String dataDiv;

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


    /**
     * 以下、レース確定後、データ入るもの
     */

    @Column
    private Integer finishedCount;

    @Column(length = 2)
    private WeatherCode weatherCd;

    @Column(length = 2)
    private TurfOrDirtConditionCode turfOrDirtCondition;

    @Type(type = "list")
    @Column(columnDefinition = "double precision[]")
    private List<Double> lapTimeItems;

    @Column
    private Double jumpMileTime;

    @Column
    private Double firstFurlong3;

    @Column
    private Double firstFurlong4;

    @Column
    private Double lastFurlong3;

    @Column
    private Double lastFurlong4;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
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
