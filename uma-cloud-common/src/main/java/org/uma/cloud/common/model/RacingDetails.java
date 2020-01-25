package org.uma.platform.common.model;

import lombok.Data;
import org.uma.platform.common.code.*;
import org.uma.platform.common.config.spec.RecordSpec;

import java.time.LocalDate;
import java.util.List;

/**
 * {@link RecordSpec.RA}
 */

@Data
public class RacingDetails {

    private RecordSpec recordType;
    private String dataDiv;
    private LocalDate dataCreateDate;

    /**
     * Unique
     * {@link HorseRacingDetails.raceId}
     * {@link RaceRefund.raceId}
     * {@link VoteCount.raceId}
     */
    private String raceId;
    private LocalDate holdingDate;
    private RaceCourseCode courseCd;
    private Integer holdingNo;
    private Integer holdingDay;
    private Integer raceNo;
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
    private RaceGradeCode gradeCd;
    private RaceGradeCode gradeCdBefore;
    private RaceTypeCode raceTypeCd;
    private RaceSignCode raceSignCd;
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
    private TrackCode trackCd;
    private TrackCode trackCdBefore;
    private String courseDiv;
    private String courseDivBefore;
    private List<Long> addedMoneyItems;
    private List<Long> addedMoneyBeforeItems;
    private List<Long> stakesMoneyItems;
    private List<Long> stakesMoneyBeforeItems;
    private String startTime;
    private String startTimeBefore;
    private String entryCount;
    private String starterCount;
    private String finishedCount;
    private WeatherCode weatherCd;
    private TurfOrDirtConditionCode turfConditionCd;
    private TurfOrDirtConditionCode dirtConditionCd;
    private List<Float> lapTimeItems;
    private String jumpMileTime;
    private Float firstFurlong3;
    private Float firstFurlong4;
    private Float lastFurlong3;
    private Float lastFurlong4;
    private List<CornerPassageRank> cornerPassageRanks;
    private String recordUpdateDiv;

    @Data
    public static class CornerPassageRank {
        private Integer corner;
        private Integer aroundCount;
        private String passageRank;
    }

}
