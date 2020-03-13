package org.uma.cloud.job.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.model.Ancestry;

public interface AncestryRepository extends JpaRepository<Ancestry, Integer> {
}
