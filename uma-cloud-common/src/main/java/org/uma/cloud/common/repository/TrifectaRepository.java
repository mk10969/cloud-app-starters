package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.model.entity.OddsTrifecta;

public interface TrifectaRepository extends JpaRepository<OddsTrifecta, String> {
}
