package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.model.entity.DiffJockey;

public interface JockeyRepository extends JpaRepository<DiffJockey, Integer> {
}
