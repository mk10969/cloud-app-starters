package org.uma.cloud.common.model.jvlink;

import lombok.Getter;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.model.BaseJvLink;

import java.time.LocalDate;

@Getter
public class RACE_JG extends BaseJvLink {
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