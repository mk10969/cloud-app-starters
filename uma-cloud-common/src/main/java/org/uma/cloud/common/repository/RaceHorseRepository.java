package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.model.RaceHorse;

public interface RaceHorseRepository extends JpaRepository<RaceHorse, Long> {
}
