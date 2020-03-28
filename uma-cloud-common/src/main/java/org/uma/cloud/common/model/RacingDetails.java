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
@Table(name = "uma_racing_details")
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

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private RaceCourseCode courseCd;

    private Integer holdingNo;

    private Integer holdingDay;

    private Integer raceNo;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
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

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private RaceGradeCode gradeCd;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private RaceGradeCode gradeCdBefore;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private RaceTypeCode raceTypeCd;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private RaceSignCode raceSignCd;

    @Column(length = 20)
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

    @Column(length = 60)
    private String raceConditionName;

    @Column(length = 4)
    private String distance;

    @Column(length = 4)
    private String distanceBefore;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private TrackCode trackCd;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
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

    // 時間だけど、Integer
    private Integer startTime;

    // 時間だけど、Integer
    private Integer startTimeBefore;

    private Integer entryCount;

    private Integer starterCount;

    private Integer finishedCount;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private WeatherCode weatherCd;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private TurfOrDirtConditionCode turfConditionCd;

    @Column(length = 20)
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

    @Column(length = 1)
    private String recordUpdateDiv;

    @Getter
    public static class CornerPassageRank {

        private Integer corner;

        private Integer aroundCount;

        private String passageRank;

    }

}
