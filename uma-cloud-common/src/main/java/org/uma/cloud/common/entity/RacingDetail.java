package org.uma.cloud.common.entity;

import lombok.Getter;
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

@Getter
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

    /**
     * 開催日をDateで所持しているので、いらんちゃいらん。
     */
//    @Column(length = 3, nullable = false)
//    private WeekDayCode weekDayCd;

    /**
     * 重症のみ設定されている数字
     * あいまいみたいなので除去。
     */
//    @Column(nullable = false)
//    private Integer specialRaceNo;

    @Column(length = 60, nullable = false)
    private String raceNameFull;

//    @Column(length = 60, nullable = false)
//    private String raceNameSub;
//
//    @Column(length = 60, nullable = false)
//    private String raceNameNote;
//
//    @Column(length = 120, nullable = false)
//    private String raceNameFullEng;
//
//    @Column(length = 120, nullable = false)
//    private String raceNameSubEng;
//
//    @Column(length = 120, nullable = false)
//    private String raceNameNoteEng;
//
//    @Column(length = 20, nullable = false)
//    private String raceNameShortChar10;
//
//    @Column(length = 12, nullable = false)
//    private String raceNameShortChar6;
//
//    @Column(length = 6, nullable = false)
//    private String raceNameShortChar3;

//    @Column(nullable = false)
//    private Integer raceNameDiv;

//    @Column(nullable = false)
//    private Integer gradeTimes;

    @Column(length = 10, nullable = false)
    private RaceGradeCode gradeCd;

//    @Column(length = 10, nullable = false)
//    private RaceGradeCode gradeCdBefore;

    @Column(length = 4, nullable = false)
    private RaceTypeCode raceTypeCd;

    @Column(length = 20, nullable = false)
    private RaceSignCode raceSignCd;

    @Column(length = 3, nullable = false)
    private WeightTypeCode weightTypeCd;

    /**
     * 2007.競走条件コード
     * は、特定のCodeクラスの型にせず、Integerとして格納する。
     */
    @Column(nullable = false)
    private Integer raceConditionCdOld2;

    @Column(nullable = false)
    private Integer raceConditionCdOld3;

    @Column(nullable = false)
    private Integer raceConditionCdOld4;

    @Column(nullable = false)
    private Integer raceConditionCdOld5;

    @Column(nullable = false)
    private Integer raceConditionCdYoungest;

    /**
     * 地方競馬のみ設定
     */
    @Column(length = 60, nullable = false)
    private String raceConditionName;

    @Column(nullable = false)
    private Integer distance;

//    @Column(nullable = false)
//    private Integer distanceBefore;

    @Column(length = 8, nullable = false)
    private TrackCode trackCd;

//    @Column(length = 6, nullable = false)
//    private TrackCode trackCdBefore;

    @Column(length = 2, nullable = false)
    private String courseDiv;

//    @Column(length = 2, nullable = false)
//    private String courseDivBefore;

    @Type(type = "list")
    @Column(columnDefinition = "integer[]", nullable = false)
    private List<Integer> addedMoneyItems;

//    @Type(type = "list")
//    @Column(columnDefinition = "integer[]", nullable = false)
//    private List<Integer> addedMoneyBeforeItems;

    @Type(type = "list")
    @Column(columnDefinition = "integer[]", nullable = false)
    private List<Integer> stakesMoneyItems;

//    @Type(type = "list")
//    @Column(columnDefinition = "integer[]", nullable = false)
//    private List<Integer> stakesMoneyBeforeItems;

    // 時間 -> LocalTime
    @Column(nullable = false)
    private LocalTime startTime;

//    // 時間 -> LocalTime
//    @Column(nullable = false)
//    private LocalTime startTimeBefore;

//    @Column(nullable = false)
//    private Integer entryCount;

    @Column(nullable = false)
    private Integer starterCount;

    @Column(nullable = false)
    private Integer finishedCount;

    @Column(length = 2, nullable = false)
    private WeatherCode weatherCd;

    @Column(length = 2, nullable = false)
    private TurfOrDirtConditionCode turfConditionCd;

    @Column(length = 2, nullable = false)
    private TurfOrDirtConditionCode dirtConditionCd;

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

//    @Column(length = 1, nullable = false)
//    private String recordUpdateDiv;

    @Getter
    public static class CornerPassageRank {

        private Integer corner;

        private Integer aroundCount;

        private String passageRank;

    }

}
