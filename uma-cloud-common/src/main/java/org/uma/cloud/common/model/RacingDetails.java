package org.uma.cloud.common.model;

import lombok.Getter;
import org.hibernate.annotations.Type;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.code.RaceGradeCode;
import org.uma.cloud.common.code.RaceSignCode;
import org.uma.cloud.common.code.RaceTypeCode;
import org.uma.cloud.common.code.TrackCode;
import org.uma.cloud.common.code.TurfOrDirtConditionCode;
import org.uma.cloud.common.code.WeatherCode;
import org.uma.cloud.common.code.WeekDayCode;
import org.uma.cloud.common.code.WeightTypeCode;
import org.uma.cloud.common.recordSpec.RecordSpec;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

/**
 * {@link RecordSpec.RA}
 */
@Getter
@Entity
@Table
public class RacingDetails extends BaseModel {

    /**
     * {@link HorseRacingDetails.raceId}
     * {@link RaceRefund.raceId}
     * {@link VoteCount.raceId}
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

    @Enumerated(EnumType.STRING)
    private WeekDayCode weekDayCd;

    private Integer specialRaceNo;

    private String raceNameFull;

    private String raceNameSub;

    private String raceNameNote;

    private String raceNameFullEng;

    private String raceNameSubEng;

    private String raceNameNoteEng;

    private String raceNameShortChar10;

    private String raceNameShortChar6;

    private String raceNameShortChar3;

    private Integer raceNameDiv;

    private Integer gradeTimes;

    @Enumerated(EnumType.STRING)
    private RaceGradeCode gradeCd;

    @Enumerated(EnumType.STRING)
    private RaceGradeCode gradeCdBefore;

    @Enumerated(EnumType.STRING)
    private RaceTypeCode raceTypeCd;

    @Enumerated(EnumType.STRING)
    private RaceSignCode raceSignCd;

    @Enumerated(EnumType.STRING)
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

    private String raceConditionName;

    private String distance;

    private String distanceBefore;

    @Enumerated(EnumType.STRING)
    private TrackCode trackCd;

    @Enumerated(EnumType.STRING)
    private TrackCode trackCdBefore;

    private String courseDiv;

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

    private String startTime;

    private String startTimeBefore;

    private String entryCount;

    private String starterCount;

    private String finishedCount;

    @Enumerated(EnumType.STRING)
    private WeatherCode weatherCd;

    @Enumerated(EnumType.STRING)
    private TurfOrDirtConditionCode turfConditionCd;

    @Enumerated(EnumType.STRING)
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

    private String recordUpdateDiv;

    @Getter
    public static class CornerPassageRank {
        private Integer corner;
        private Integer aroundCount;
        private String passageRank;
    }

}
