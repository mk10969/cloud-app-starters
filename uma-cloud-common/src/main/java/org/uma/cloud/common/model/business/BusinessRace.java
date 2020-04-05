package org.uma.cloud.common.model.business;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
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

}
