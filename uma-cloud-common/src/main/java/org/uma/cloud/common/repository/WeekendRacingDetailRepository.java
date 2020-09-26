package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.entity.WeekendRacingDetail;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface WeekendRacingDetailRepository extends JpaRepository<WeekendRacingDetail, String> {

    /**
     * 現在時刻で検索し、未来日付のデータを取得
     *
     * @param date 現在Date
     * @param time 現在time
     * @return BusinessRacing
     */
    List<WeekendRacingDetail> findByHoldingDateAfterAndStartTimeAfter(LocalDate date, LocalTime time);

//    /**
//     * 現在時刻とデータ区分で検索し、過去日付のデータを取得
//     *
//     * @param dataDiv データ区分
//     * @param now     現在時刻
//     * @return BusinessRacing
//     */
//    List<BusinessRacing> findByDataDivAndRaceStartDateTimeBefore(String dataDiv, LocalDateTime now);

}
