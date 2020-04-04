package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.code.RaceGradeCode;
import org.uma.cloud.common.code.TurfOrDirtConditionCode;
import org.uma.cloud.common.model.RacingDetails;

import java.time.LocalDate;
import java.util.List;

public interface RacingDetailsRepository extends JpaRepository<RacingDetails, String> {

    /**
     * レース開催日で検索
     *
     * @param bottom
     * @param top
     * @return
     */
    List<RacingDetails> findByHoldingDateBetween(LocalDate bottom, LocalDate top);

    /**
     * レースコードで検索
     *
     * @param raceCourseCode
     * @return
     */
    List<RacingDetails> findByCourseCd(RaceCourseCode raceCourseCode);

    /**
     * レースのグレードで検索
     *
     * @param raceGradeCode
     * @return
     */
    List<RacingDetails> findByGradeCd(RaceGradeCode raceGradeCode);

    /**
     * 馬場状態で検索
     *
     * @param turfOrDirtConditionCode
     * @return
     */
    List<RacingDetails> findByTurfConditionCd(TurfOrDirtConditionCode turfOrDirtConditionCode);


}
