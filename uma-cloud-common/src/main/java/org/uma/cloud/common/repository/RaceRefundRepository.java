package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.model.RaceRefund;

public interface RaceRefundRepository extends JpaRepository<RaceRefund, String> {
}