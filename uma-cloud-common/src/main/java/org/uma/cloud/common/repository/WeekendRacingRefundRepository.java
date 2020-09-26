package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.entity.WeekendRacingRefund;

public interface WeekendRacingRefundRepository
        extends JpaRepository<WeekendRacingRefund, WeekendRacingRefund.CompositeId> {
}
