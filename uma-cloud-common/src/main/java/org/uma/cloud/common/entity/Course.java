package org.uma.cloud.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.code.TrackCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@IdClass(Course.CompositeId.class)
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


    @Override
    public Object getPrimaryKey() {
        CompositeId compositeId = new CompositeId();
        compositeId.setCourseCd(this.courseCd);
        compositeId.setDistance(this.distance);
        compositeId.setTrackCd(this.trackCd);
        compositeId.setCourseRepairDate(this.courseRepairDate);
        return compositeId;
    }


    @Data
    public static class CompositeId implements Serializable {

        private RaceCourseCode courseCd;

        private Integer distance;

        private TrackCode trackCd;

        private LocalDate courseRepairDate;
    }

}
