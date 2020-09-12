package org.uma.cloud.common.model.jvlink;

import lombok.Getter;
import org.uma.cloud.common.code.RaceCourseCode;

import java.time.LocalDate;

@Getter
class RACE_JG {
    private String dataDiv;
    private String raceId;
    private LocalDate holdingDate;
    private RaceCourseCode courseCd;
    private Integer holdingNo;
    private Integer holdingDay;
    private Integer raceNo;
    private Long bloodlineNo;
    private String horseName;
    private Integer entryOrderNo;
    private Integer entryDiv;
    private Integer exclusionState;
}