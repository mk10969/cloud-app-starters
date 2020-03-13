package org.uma.cloud.job.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.model.RacingDetails;

public interface RacingDetailsRepository  extends JpaRepository<RacingDetails, String> {
}
