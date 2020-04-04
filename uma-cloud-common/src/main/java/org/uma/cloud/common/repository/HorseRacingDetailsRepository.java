package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.model.HorseRacingDetails;

import java.util.List;

public interface HorseRacingDetailsRepository extends JpaRepository<HorseRacingDetails, HorseRacingDetails.HorseRacingDetailsId> {

    /**
     * レースIDで検索
     *
     * @param raceId
     * @return
     */
    List<HorseRacingDetails> findByRaceId(String raceId);

    /**
     * 血統登録番号で検索
     *
     * @param bloodlineNo
     * @return
     */
    List<HorseRacingDetails> findByBloodlineNo(Long bloodlineNo);

    /**
     * レースコードで検索
     *
     * @param raceCourseCode
     * @return
     */
    List<HorseRacingDetails> findByCourseCd(RaceCourseCode raceCourseCode);


}
