package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.entity.OddsTrio;

public interface TrioRepository extends JpaRepository<OddsTrio, String> {
}
