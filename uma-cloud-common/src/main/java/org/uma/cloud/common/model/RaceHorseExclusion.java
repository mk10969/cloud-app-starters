package org.uma.cloud.common.model;


import lombok.Data;
import lombok.Getter;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.recordSpec.RecordSpec;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * {@link RecordSpec.JG}
 */
@Getter
@Entity
@Table
public class RaceHorseExclusion extends BaseModel {

    /**
     * {@link RacingDetails.raceId}
     */
    @Id
    @Column(length = 16)
    private String raceId;

    private LocalDate holdingDate;
    private RaceCourseCode courseCd;
    private Integer holdingNo;
    private Integer holdingDay;
    private Integer raceNo;

    /**
     * 血統登録番号 10桁
     * {@link Offspring.bloodlineNo}
     */
    private Long bloodlineNo;

    @Column(length = 36)
    private String horseName;

    /**
     * 同一業務日付で、同一馬を受付（出馬投票の追加、再投票）した順番
     */
    private Integer entryOrderNo;

    /**
     * 1:投票馬
     * 2:締切での除外馬
     * 4:再投票馬
     * 5:再投票除外馬
     * 6:馬番を付さない出走取消馬
     * 9:取消馬
     */
    private Integer entryDiv;

    /**
     * 1:非抽選馬
     * 2:非当選馬
     */
    private Integer exclusionState;


    @Data
    static class RaceHorseExclusionId {

        private String raceId;

        private Long bloodlineNo;

        private Integer entryOrderNo;

    }

}
