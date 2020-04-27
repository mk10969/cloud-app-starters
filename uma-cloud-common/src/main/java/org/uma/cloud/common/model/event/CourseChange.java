package org.uma.cloud.common.model.event;

import lombok.Getter;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.code.TrackCode;
import org.uma.cloud.common.model.BaseModel;
import org.uma.cloud.common.recordSpec.RecordSpec;

import java.time.LocalDate;

/**
 * {@link RecordSpec.CC}
 */
@Getter
public class CourseChange extends BaseModel {

    /**
     * 主キー
     */
    private String raceId;

    private LocalDate holdingDate;

    private RaceCourseCode courseCd;

    private Integer holdingNo;

    private Integer holdingDay;

    private Integer raceNo;

    private String announceDate;

    private Integer distanceAfter;

    private TrackCode trackCdAfter;

    private Integer distanceBefore;

    private TrackCode trackCdBefore;

    /**
     * 1:強風
     * 2:台風
     * 3:雪
     * 4:その他
     */
    private Integer reason;

}
