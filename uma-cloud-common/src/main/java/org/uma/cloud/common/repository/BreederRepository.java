package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.model.entity.DiffBreeder;

public interface BreederRepository extends JpaRepository<DiffBreeder, Integer> {
}
