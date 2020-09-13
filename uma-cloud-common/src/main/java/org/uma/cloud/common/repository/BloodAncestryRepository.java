package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.entity.BloodAncestry;

public interface BloodAncestryRepository extends JpaRepository<BloodAncestry, Integer> {
}
