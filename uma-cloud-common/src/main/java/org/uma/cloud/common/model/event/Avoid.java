package org.uma.cloud.common.model.event;

import lombok.Getter;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.code.RecordSpec;
import org.uma.cloud.common.model.BaseJvLink;

import java.time.LocalDate;

/**
 * {@link RecordSpec.AV}
 */
@Getter
public class Avoid extends BaseJvLink {

    /**
     * データ区分
     * 1:出走取消
     * 2:競走除外
     */
    private String dataDiv;

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
