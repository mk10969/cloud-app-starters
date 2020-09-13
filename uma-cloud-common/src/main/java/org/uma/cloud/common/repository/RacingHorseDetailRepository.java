package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.entity.RacingHorseDetail;

import java.util.List;

public interface RacingHorseDetailRepository extends JpaRepository<RacingHorseDetail, RacingHorseDetail.CompositeId> {

    /**
     * レースIDで検索
     *
     * @param raceId
     * @return
     */
    List<RacingHorseDetail> findByRaceId(String raceId);

    /**
     * 血統登録番号で検索
     *
     * @param bloodlineNo
     * @return
     */
    List<RacingHorseDetail> findByBloodlineNo(Long bloodlineNo);

    // RacingDetailにあるから、ここのはなくす。
//    /**
//     * レースコードで検索
//     *
//     * @param raceCourseCode
//     * @return
//     */
//    List<RacingHorseDetail> findByCourseCd(RaceCourseCode raceCourseCode);


}
