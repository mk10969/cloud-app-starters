package org.uma.cloud.common.model;

import lombok.Getter;
import org.hibernate.annotations.Type;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.code.RaceGradeCode;
import org.uma.cloud.common.code.RaceSignCode;
import org.uma.cloud.common.code.RaceTypeCode;
import org.uma.cloud.common.code.RecordSpec;
import org.uma.cloud.common.code.TrackCode;
import org.uma.cloud.common.code.TurfOrDirtConditionCode;
import org.uma.cloud.common.code.WeatherCode;
import org.uma.cloud.common.code.WeekDayCode;
import org.uma.cloud.common.code.WeightTypeCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * {@link RecordSpec.RA}
 */
@Getter
@Entity
@Table
public class RacingDetail extends BaseModel {

    /**
     * データ区分
     */
    @Column(length = 1)
    private String dataDiv;

    /**
     * {@link RacingHorseDetail.raceId}
     * {@link RacingRefund.raceId}
     * {@link RacingVote.raceId}
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

    @Column(length = 3)
    private WeekDayCode weekDayCd;

    private Integer specialRaceNo;

    @Column(length = 60)
    private String raceNameFull;

    @Column(length = 60)
    private String raceNameSub;

    @Column(length = 60)
    private String raceNameNote;

    @Column(length = 120)
    private String raceNameFullEng;

    @Column(length = 120)
    private String raceNameSubEng;

    @Column(length = 120)
    private String raceNameNoteEng;

    @Column(length = 20)
    private String raceNameShortChar10;

    @Column(length = 12)
    private String raceNameShortChar6;

    @Column(length = 6)
    private String raceNameShortChar3;

    private Integer raceNameDiv;

    private Integer gradeTimes;

    @Column(length = 10)
    private RaceGradeCode gradeCd;

    @Column(length = 10)
    private RaceGradeCode gradeCdBefore;

    @Column(length = 4)
    private RaceTypeCode raceTypeCd;

    @Column(length = 20)
    private RaceSignCode raceSignCd;

    @Column(length = 3)
    private WeightTypeCode weightTypeCd;

    /**
     * 2007.競走条件コード
     * は、特定のCodeクラスの型にせず、Integerとして格納する。
     */
    private Integer raceConditionCdOld2;

    private Integer raceConditionCdOld3;

    private Integer raceConditionCdOld4;

    private Integer raceConditionCdOld5;

    private Integer raceConditionCdYoungest;

    @Column(length = 60)
    private String raceConditionName;

    private Integer distance;

    private Integer distanceBefore;

    @Column(length = 6)
    private TrackCode trackCd;

    @Column(length = 6)
    private TrackCode trackCdBefore;

    @Column(length = 2)
    private String courseDiv;

    @Column(length = 2)
    private String courseDivBefore;

    @Type(type = "list")
    @Column(columnDefinition = "integer[]")
    private List<Integer> addedMoneyItems;

    @Type(type = "list")
    @Column(columnDefinition = "integer[]")
    private List<Integer> addedMoneyBeforeItems;

    @Type(type = "list")
    @Column(columnDefinition = "integer[]")
    private List<Integer> stakesMoneyItems;

    @Type(type = "list")
    @Column(columnDefinition = "integer[]")
    private List<Integer> stakesMoneyBeforeItems;

    // 時間 -> LocalTime
    private LocalTime startTime;

    // 時間 -> LocalTime
    private LocalTime startTimeBefore;

    private Integer entryCount;

    private Integer starterCount;

    private Integer finishedCount;

    @Column(length = 2)
    private WeatherCode weatherCd;

    @Column(length = 2)
    private TurfOrDirtConditionCode turfConditionCd;

    @Column(length = 2)
    private TurfOrDirtConditionCode dirtConditionCd;

    @Type(type = "list")
    @Column(columnDefinition = "double precision[]")
    private List<Double> lapTimeItems;

    private Double jumpMileTime;

    private Double firstFurlong3;

    private Double firstFurlong4;

    private Double lastFurlong3;

    private Double lastFurlong4;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<CornerPassageRank> cornerPassageRanks;

    @Column(length = 1)
    private String recordUpdateDiv;

    @Getter
    public static class CornerPassageRank {

        private Integer corner;

        private Integer aroundCount;

        private String passageRank;

    }

}
