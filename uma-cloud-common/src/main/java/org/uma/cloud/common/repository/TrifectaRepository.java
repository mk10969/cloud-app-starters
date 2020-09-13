package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.entity.OddsTrifecta;

public interface TrifectaRepository extends JpaRepository<OddsTrifecta, String> {
}
