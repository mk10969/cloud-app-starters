package org.uma.cloud.common.repository.odds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.model.OddsExacta;

public interface ExactaRepository extends JpaRepository<OddsExacta, String> {
}
