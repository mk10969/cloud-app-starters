package org.uma.cloud.common.repository.business;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.model.business.BusinessRacingHorse;

import java.util.List;

public interface BusinessRacingHorseRepository
        extends JpaRepository<BusinessRacingHorse, BusinessRacingHorse.CompositeId> {

    /**
     * レースIDで検索
     *
     * @param raceId
     * @return
     */
    List<BusinessRacingHorse> findByRaceId(String raceId);

}
