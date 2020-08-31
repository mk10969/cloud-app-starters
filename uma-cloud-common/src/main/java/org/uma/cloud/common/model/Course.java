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
    @Column(length = 1, nullable = false)
    private String dataDiv;

    /**
     * コースコード
     */
    @Id
    @Column(length = 6, nullable = false)
    private RaceCourseCode courseCd;

    @Id
    @Column(nullable = false)
    private Integer distance;

    @Id
    @Column(length = 6, nullable = false)
    private TrackCode trackCd;

    @Id
    @Column(nullable = false)
    private LocalDate courseRepairDate;

    @Column(length = 3000, nullable = false)
    private String courseDescription;


    @Data
    public static class CompositeId implements Serializable {

        private RaceCourseCode courseCd;

        private Integer distance;

        private TrackCode trackCd;

        private LocalDate courseRepairDate;
    }

}
