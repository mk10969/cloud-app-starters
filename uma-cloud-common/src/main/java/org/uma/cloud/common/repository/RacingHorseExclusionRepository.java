package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.model.RacingHorseExclusion;

public interface RacingHorseExclusionRepository extends JpaRepository<RacingHorseExclusion, RacingHorseExclusion.CompositeId> {
}