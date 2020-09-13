package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.entity.BloodLine;

public interface BloodLineRepository extends JpaRepository<BloodLine, Long> {
}
