package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.entity.OddsQuinella;

public interface QuinellaRepository extends JpaRepository<OddsQuinella, String> {
}
