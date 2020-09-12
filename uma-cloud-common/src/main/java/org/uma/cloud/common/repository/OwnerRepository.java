package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.model.entity.DiffOwner;

public interface OwnerRepository extends JpaRepository<DiffOwner, Integer> {
}
