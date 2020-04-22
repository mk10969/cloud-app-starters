package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.code.RaceGradeCode;
import org.uma.cloud.common.code.TurfOrDirtConditionCode;
import org.uma.cloud.common.model.RacingDetail;

import java.time.LocalDate;
import java.util.List;

public interface RacingDetailRepository extends JpaRepository<RacingDetail, String> {

    /**
     * レース開催日を期間で検索
     *
     * @param bottom
     * @param top
     * @return
     */
    List<RacingDetail> findByHoldingDateBetween(LocalDate bottom, LocalDate top);

    /**
     * レースコードで検索
     *
     * @param raceCourseCode
     * @return
     */
    List<RacingDetail> findByCourseCd(RaceCourseCode raceCourseCode);

    /**
     * レースのグレードで検索
     *
     * @param raceGradeCode
     * @return
     */
    List<RacingDetail> findByGradeCd(RaceGradeCode raceGradeCode);

    /**
     * 馬場状態で検索
     *
     * @param turfOrDirtConditionCode
     * @return
     */
    List<RacingDetail> findByTurfConditionCd(TurfOrDirtConditionCode turfOrDirtConditionCode);


}
