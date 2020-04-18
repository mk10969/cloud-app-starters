package org.uma.cloud.common.repository.business;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.model.business.BusinessRacingHorse;

public interface BusinessRacingHorseRepository extends JpaRepository<BusinessRacingHorse, BusinessRacingHorse.CompositeId> {
}
