package org.uma.cloud.common.model.event;

import lombok.Getter;
import org.uma.cloud.common.code.JockeyApprenticeCode;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.model.BaseModel;
import org.uma.cloud.common.recordSpec.RecordSpec;

import java.time.LocalDate;

/**
 * {@link RecordSpec.JC}
 */
@Getter
public class JockeyChange extends BaseModel {

    /**
     * Id
     */
    private String raceId;

    private LocalDate holdingDate;

    private RaceCourseCode courseCd;

    private Integer holdingNo;

    private Integer holdingDay;

    private Integer raceNo;

    private String announceDate;

    /**
     * Id
     */
    private String horseNo;

    private String horseName;

    // after
    private Double loadWeightAfter;

    private Integer jockeyCdAfter;

    private String jockeyNameAfter;

    private JockeyApprenticeCode jockeyApprenticeCdAfter;

    // before
    private Double loadWeightBefore;

    private Integer jockeyCdBefore;

    private String jockeyNameBefore;

    private JockeyApprenticeCode jockeyApprenticeCdBefore;

}
