package org.uma.cloud.common.model.event;

import lombok.Getter;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.code.RecordSpec;
import org.uma.cloud.common.model.entity.BaseModel;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * {@link RecordSpec.TC}
 */
@Getter
public class TimeChange extends BaseModel {

    /**
     * データ区分
     */
    private String dataDiv;

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

    /**
     * 変更後、発走開始時刻
     */
    private LocalTime startTimeAfter;

    /**
     * 変更前、発走開始時刻
     */
    private LocalTime startTimeBefore;

}
