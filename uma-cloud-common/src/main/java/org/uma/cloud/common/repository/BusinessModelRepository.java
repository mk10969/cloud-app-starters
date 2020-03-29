package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.model.BusinessModel;

public interface BusinessModelRepository extends JpaRepository<BusinessModel, Integer> {

    BusinessModel findTopByOrderByIdDesc();
}
