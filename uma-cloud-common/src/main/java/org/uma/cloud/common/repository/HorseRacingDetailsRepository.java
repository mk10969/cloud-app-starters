package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.model.HorseRacingDetails;

public interface HorseRacingDetailsRepository extends JpaRepository<HorseRacingDetails, String> {
}
