package org.uma.cloud.common.model.event;

import lombok.Getter;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.code.RecordSpec;
import org.uma.cloud.common.model.BaseModel;

import java.time.LocalDate;
import java.util.List;

/**
 * {@link RecordSpec.WH}
 */
@Getter
public class Weight extends BaseModel {

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

    private List<HorseWeight> horseWeights;


    @Getter
    public static class HorseWeight {

        private String horseNo;

        private String horseName;

        private Integer horseWeight;

        private String changeSign;

        private Integer changeAmount;
    }

}
