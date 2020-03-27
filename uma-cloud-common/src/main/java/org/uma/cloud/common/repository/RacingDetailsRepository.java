package org.uma.cloud.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uma.cloud.common.model.RacingDetails;


@Repository
public interface RacingDetailsRepository extends JpaRepository<RacingDetails, String> {
}
