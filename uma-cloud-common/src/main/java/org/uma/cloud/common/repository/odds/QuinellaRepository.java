package org.uma.cloud.common.repository.odds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.model.odds.Quinella;

public interface QuinellaRepository extends JpaRepository<Quinella, String> {
}
