package org.uma.cloud.common.repository.odds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.model.OddsTrio;

public interface TrioRepository extends JpaRepository<OddsTrio, String> {
}
