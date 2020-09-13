package org.uma.cloud.common.model.jvlink;

import lombok.Getter;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.code.RaceGradeCode;
import org.uma.cloud.common.code.RaceSignCode;
import org.uma.cloud.common.code.RaceTypeCode;
import org.uma.cloud.common.code.TrackCode;
import org.uma.cloud.common.code.TurfOrDirtConditionCode;
import org.uma.cloud.common.code.WeatherCode;
import org.uma.cloud.common.code.WeekDayCode;
import org.uma.cloud.common.code.WeightTypeCode;
import org.uma.cloud.common.model.BaseJvLink;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
public class RACE_RA extends BaseJvLink {
    private String dataDiv;
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
    private Integer raceConditionCdOld2;
    private Integer raceConditionCdOld3;
    private Integer raceConditionCdOld4;
    private Integer raceConditionCdOld5;
    private Integer raceConditionCdYoungest;
    private String raceConditionName;
    private Integer distance;
    private Integer distanceBefore;
    private TrackCode trackCd;
    private TrackCode trackCdBefore;
    private String courseDiv;
    private String courseDivBefore;
    private List<Integer> addedMoneyItems;
    private List<Integer> addedMoneyBeforeItems;
    private List<Integer> stakesMoneyItems;
    private List<Integer> stakesMoneyBeforeItems;
    private LocalTime startTime;
    private LocalTime startTimeBefore;
    private Integer entryCount;
    private Integer starterCount;
    private Integer finishedCount;
    private WeatherCode weatherCd;
    private TurfOrDirtConditionCode turfConditionCd;
    private TurfOrDirtConditionCode dirtConditionCd;
    private List<Double> lapTimeItems;
    private Double jumpMileTime;
    private Double firstFurlong3;
    private Double firstFurlong4;
    private Double lastFurlong3;
    private Double lastFurlong4;
    private List<CornerPassageRank> cornerPassageRanks;
    private String recordUpdateDiv;

    @Getter
    public static class CornerPassageRank {
        private Integer corner;
        private Integer aroundCount;
        private String passageRank;
    }
}