package org.uma.cloud.common.model;

import lombok.Data;
import lombok.Getter;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.code.RecordSpec;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * {@link RecordSpec.JG}
 */
@Getter
@Entity
@IdClass(RacingHorseExclusion.CompositeId.class)
@Table
public class RacingHorseExclusion extends BaseModel {

    /**
     * データ区分
     */
    @Column(length = 1, nullable = false)
    private String dataDiv;

    /**
     * {@link RacingDetail.raceId}
     */
    @Id
    @Column(length = 16, nullable = false)
    private String raceId;

    @Column(nullable = false)
    private LocalDate holdingDate;

    @Column(length = 6, nullable = false)
    private RaceCourseCode courseCd;

    @Column(nullable = false)
    private Integer holdingNo;

    @Column(nullable = false)
    private Integer holdingDay;

    @Column(nullable = false)
    private Integer raceNo;

    /**
     * 血統登録番号 10桁
     * {@link BloodLine.bloodlineNo}
     */
    @Id
    @Column(nullable = false)
    private Long bloodlineNo;

    @Column(length = 36, nullable = false)
    private String horseName;

    /**
     * 同一業務日付で、同一馬を受付（出馬投票の追加、再投票）した順番
     */
    @Id
    @Column(nullable = false)
    private Integer entryOrderNo;

    /**
     * 1:投票馬
     * 2:締切での除外馬
     * 4:再投票馬
     * 5:再投票除外馬
     * 6:馬番を付さない出走取消馬
     * 9:取消馬
     */
    @Column(nullable = false)
    private Integer entryDiv;

    /**
     * 1:非抽選馬
     * 2:非当選馬
     */
    @Column(nullable = false)
    private Integer exclusionState;


    @Data
    public static class CompositeId implements Serializable {

        private String raceId;

        private Long bloodlineNo;

        private Integer entryOrderNo;

    }

}
