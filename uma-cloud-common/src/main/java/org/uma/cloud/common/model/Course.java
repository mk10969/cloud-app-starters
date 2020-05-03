package org.uma.cloud.common.model;

import lombok.Data;
import lombok.Getter;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.code.RecordSpec;
import org.uma.cloud.common.code.TrackCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * {@link RecordSpec.CS}
 */
@Getter
@Entity
@IdClass(Course.CompositeId.class)
@Table
public class Course extends BaseModel {

    /**
     * データ区分
     */
    @Column(length = 1)
    private String dataDiv;

    /**
     * コースコード
     */
    @Id
    @Column(length = 6)
    private RaceCourseCode courseCd;

    @Id
    private Integer distance;

    @Id
    @Column(length = 6)
    private TrackCode trackCd;

    @Id
    private LocalDate courseRepairDate;

    @Column(length = 3000)
    private String courseDescription;


    @Data
    public static class CompositeId implements Serializable {

        private RaceCourseCode courseCd;

        private Integer distance;

        private TrackCode trackCd;

        private LocalDate courseRepairDate;
    }

}
