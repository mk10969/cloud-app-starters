package org.uma.cloud.common.repository.business;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.model.business.BusinessRacing;

import java.time.LocalDateTime;
import java.util.List;

public interface BusinessRacingRepository extends JpaRepository<BusinessRacing, String> {

    /**
     * 現在時刻で検索し、未来日付を取得
     *
     * @param now
     * @return
     */
    List<BusinessRacing> findByRaceStartDateTimeAfter(LocalDateTime now);


    List<BusinessRacing> findByDataDivAndRaceStartDateTimeBefore(String dataDiv, LocalDateTime now);

}
