package org.uma.cloud.common.model.jvlink;

import lombok.Getter;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.code.TrackCode;
import org.uma.cloud.common.model.JvLinkBase;

import java.time.LocalDate;

@Getter
public class COMM_CS extends JvLinkBase {
    private String dataDiv;
    private RaceCourseCode courseCd;
    private Integer distance;
    private TrackCode trackCd;
    private LocalDate courseRepairDate;
    private String courseDescription;
}