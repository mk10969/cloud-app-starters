package org.uma.cloud.common.model.business;

import lombok.Data;
import org.uma.cloud.common.code.RaceCourseCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "business_race")
public class BusinessRace {

    /**
     * レースID
     */
    @Id
    private String raceId;

    /**
     * レース開始時刻
     */
    @Column(nullable = false)
    private LocalDateTime raceStartDateTime;

    /**
     * データ区分
     * "2" になるデータになるはず
     */
    @Column(length = 1)
    private String dataDiv;

    /**
     * なんとなくこれ入れときます。
     */
    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private RaceCourseCode courseCd;

    /**
     * 同上
     */
    @Column(length = 60)
    private String raceNameFull;

}
