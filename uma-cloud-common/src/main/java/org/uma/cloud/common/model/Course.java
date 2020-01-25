package org.uma.cloud.common.model;

import lombok.Getter;
import lombok.Setter;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.code.TrackCode;
import org.uma.cloud.common.recordSpec.RecordSpec;

import java.time.LocalDate;

/**
 * {@link RecordSpec.CS}
 */
@Getter
@Setter
public class Course extends BaseModel {

    /**
     * コースコード
     */
    private RaceCourseCode courseCd;
    private Integer distance;
    private TrackCode trackCd;
    private LocalDate courseRepairDate;
    private String courseDescription;

}
