package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.entity.RacingHorseExclusion;

public interface RacingHorseExclusionRepository extends JpaRepository<RacingHorseExclusion, RacingHorseExclusion.CompositeId> {
}
