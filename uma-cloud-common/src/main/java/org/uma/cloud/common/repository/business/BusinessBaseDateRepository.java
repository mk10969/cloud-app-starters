package org.uma.cloud.common.repository.business;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.model.business.BusinessBaseDate;

public interface BusinessBaseDateRepository extends JpaRepository<BusinessBaseDate, Integer> {

    /**
     * tail -1 のデータを返す。
     *
     * @return
     */
    BusinessBaseDate findTopByOrderByIdDesc();
}