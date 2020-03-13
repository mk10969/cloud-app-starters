package org.uma.cloud.job.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.model.Jockey;

public interface JockeyRepository extends JpaRepository<Jockey, Integer> {
}
