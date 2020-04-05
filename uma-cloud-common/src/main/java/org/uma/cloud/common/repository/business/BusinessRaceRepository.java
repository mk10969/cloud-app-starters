package org.uma.cloud.common.repository.business;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.model.business.BusinessRace;

import java.time.LocalDateTime;
import java.util.List;

public interface BusinessRaceRepository extends JpaRepository<BusinessRace, String> {

    /**
     * 現在時刻で検索
     *
     * @param now
     * @return
     */
    List<BusinessRace> findByRaceStartDateTime(LocalDateTime now);

}
