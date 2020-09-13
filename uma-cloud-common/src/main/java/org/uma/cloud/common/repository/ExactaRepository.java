package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.entity.OddsExacta;

public interface ExactaRepository extends JpaRepository<OddsExacta, String> {
}
