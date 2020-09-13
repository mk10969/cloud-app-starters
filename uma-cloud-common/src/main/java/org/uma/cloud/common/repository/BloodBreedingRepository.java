package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.entity.BloodBreeding;

public interface BloodBreedingRepository extends JpaRepository<BloodBreeding, Integer> {
}
