package org.uma.cloud.common.model;

import lombok.Data;
import lombok.Getter;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.code.TrackCode;
import org.uma.cloud.common.recordSpec.RecordSpec;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "umm_course")
public class Course extends BaseModel {

    /**
     * コースコード
     */
    @Id
    @Enumerated(EnumType.STRING)
    private RaceCourseCode courseCd;

    @Id
    private Integer distance;

    @Id
    @Enumerated(EnumType.STRING)
    private TrackCode trackCd;

    @Id
    private LocalDate courseRepairDate;

    @Column(length = 3000)
    private String courseDescription;


    @Data
    public static class CourseId implements Serializable {

        @Enumerated(EnumType.STRING)
        private RaceCourseCode courseCd;

        private Integer distance;

        @Enumerated(EnumType.STRING)
        private TrackCode trackCd;

        private LocalDate courseRepairDate;

    }

}
