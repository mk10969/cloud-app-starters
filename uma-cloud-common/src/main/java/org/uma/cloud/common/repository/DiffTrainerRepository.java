package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.entity.DiffTrainer;

public interface DiffTrainerRepository extends JpaRepository<DiffTrainer, Integer> {
}
