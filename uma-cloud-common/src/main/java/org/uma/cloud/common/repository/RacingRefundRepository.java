package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.entity.RacingRefund;

public interface RacingRefundRepository extends JpaRepository<RacingRefund, RacingRefund.CompositeId> {
}
