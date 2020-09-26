package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.entity.DiffOwner;

public interface DiffOwnerRepository extends JpaRepository<DiffOwner, Integer> {
}
