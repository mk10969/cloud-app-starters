package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.model.RaceHorse;

import java.util.List;

public interface RaceHorseRepository extends JpaRepository<RaceHorse, RaceHorse.CompositeId> {

    /**
     * 血統登録番号で検索
     *
     * @param bloodlineNo
     * @return
     */
    List<RaceHorse> findByBloodlineNo(long bloodlineNo);

}
