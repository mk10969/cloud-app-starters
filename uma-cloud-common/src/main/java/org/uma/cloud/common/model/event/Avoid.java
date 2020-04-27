package org.uma.cloud.common.model.event;

import lombok.Getter;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.model.BaseModel;
import org.uma.cloud.common.recordSpec.RecordSpec;

import java.time.LocalDate;

/**
 * {@link RecordSpec.AV}
 */
@Getter
public class Avoid extends BaseModel {

    /**
     *{@link BaseModel.dataDiv}にて判別。
     *
     * 1:出走取消
     * 2:競走除外
     */

    /**
     * レース特定キー
     */
    private String raceId;

    private LocalDate holdingDate;

    private RaceCourseCode courseCd;

    private Integer holdingNo;

    private Integer holdingDay;

    private Integer raceNo;

    private String announceDate;

    /**
     * 馬番特定キー
     */
    private String horseNo;

    private String horseName;

    /**
     * 000:初期値
     * 001:疾病
     * 002:事故
     * 003:その他
     */
    private String reason;

}
