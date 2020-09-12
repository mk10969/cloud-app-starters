package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.model.entity.DiffTrainer;

public interface TrainerRepository extends JpaRepository<DiffTrainer, Integer> {
}
