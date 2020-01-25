package org.uma.platform.common.model;

import lombok.Data;
import org.uma.platform.common.code.RaceCourseCode;
import org.uma.platform.common.code.TrackCode;
import org.uma.platform.common.config.spec.RecordSpec;

import java.time.LocalDate;

/**
 * {@link RecordSpec.CS}
 */

@Data
public class Course {

    private RecordSpec recordType;
    private String dataDiv;
    private LocalDate dataCreateDate;

    /**
     * コースコード
     */
    private RaceCourseCode courseCd;
    private Integer distance;
    private TrackCode trackCd;
    private LocalDate courseRepairDate;
    private String courseDescription;

}
