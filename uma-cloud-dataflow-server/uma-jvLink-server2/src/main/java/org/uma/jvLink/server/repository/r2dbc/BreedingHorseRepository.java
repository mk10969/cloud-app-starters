package org.uma.cloud.job.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.model.BreedingHorse;

public interface BreedingHorseRepository extends JpaRepository<BreedingHorse, Integer> {
}
