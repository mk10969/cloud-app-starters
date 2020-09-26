package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.entity.WeekendRacingHorseDetail;

import java.util.List;

public interface WeekendRacingHorseDetailRepository
        extends JpaRepository<WeekendRacingHorseDetail, WeekendRacingHorseDetail.CompositeId> {

    /**
     * レースIDで検索
     *
     * @param raceId
     * @return
     */
    List<WeekendRacingHorseDetail> findByRaceId(String raceId);

}
