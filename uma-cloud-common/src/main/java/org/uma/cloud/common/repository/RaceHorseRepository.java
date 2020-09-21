package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.entity.DiffRaceHorse;

public interface RaceHorseRepository extends JpaRepository<DiffRaceHorse, Long> {
}
