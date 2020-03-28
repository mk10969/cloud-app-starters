package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.model.Offspring;

public interface OffspringRepository extends JpaRepository<Offspring, Long> {
}
