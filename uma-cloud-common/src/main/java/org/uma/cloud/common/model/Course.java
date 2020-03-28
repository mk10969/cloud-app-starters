package org.uma.cloud.common.model;

import lombok.Data;
import lombok.Getter;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.code.TrackCode;
import org.uma.cloud.common.recordSpec.RecordSpec;

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
@IdClass(Course.CourseId.class)
@Table
public class Course extends BaseModel {

    /**
     * コースコード
     */
    @Id
    private RaceCourseCode courseCd;

    @Id
    private Integer distance;

    @Id
    private TrackCode trackCd;

    @Id
    private LocalDate courseRepairDate;

    @Column(length = 3000)
    private String courseDescription;


    @Data
    public static class CourseId implements Serializable {

        private RaceCourseCode courseCd;

        private Integer distance;

        private TrackCode trackCd;

        private LocalDate courseRepairDate;

    }

}
