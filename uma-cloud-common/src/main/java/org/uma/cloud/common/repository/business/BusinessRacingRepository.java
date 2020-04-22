package org.uma.cloud.common.repository.business;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.model.business.BusinessRacing;

import java.time.LocalDateTime;
import java.util.List;

public interface BusinessRacingRepository extends JpaRepository<BusinessRacing, String> {

    /**
     * 現在時刻で検索し、未来日付のデータを取得
     *
     * @param now    現在時刻
     * @return       BusinessRacing
     */
    List<BusinessRacing> findByRaceStartDateTimeAfter(LocalDateTime now);

    /**
     * 現在時刻とデータ区分で検索し、過去日付のデータを取得
     *
     * @param dataDiv データ区分
     * @param now     現在時刻
     * @return        BusinessRacing
     */
    List<BusinessRacing> findByDataDivAndRaceStartDateTimeBefore(String dataDiv, LocalDateTime now);

}
