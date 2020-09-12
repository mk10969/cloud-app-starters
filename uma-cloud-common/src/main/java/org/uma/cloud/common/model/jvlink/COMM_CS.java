package org.uma.cloud.common.model.jvlink;

import lombok.Getter;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.code.TrackCode;

import java.time.LocalDate;

@Getter
class COMM_CS {
    private String dataDiv;
    private RaceCourseCode courseCd;
    private Integer distance;
    private TrackCode trackCd;
    private LocalDate courseRepairDate;
    private String courseDescription;
}