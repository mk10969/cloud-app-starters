package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.entity.RacingOdds;

public interface RacingOddsRepository extends JpaRepository<RacingOdds, RacingOdds.CompositeId> {
}
