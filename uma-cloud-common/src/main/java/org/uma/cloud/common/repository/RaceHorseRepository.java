package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.model.entity.DiffRaceHorse;

import java.util.List;

public interface RaceHorseRepository extends JpaRepository<DiffRaceHorse, DiffRaceHorse.CompositeId> {

    /**
     * 血統登録番号で検索
     *
     * @param bloodlineNo
     * @return
     */
    List<DiffRaceHorse> findByBloodlineNo(long bloodlineNo);

}
