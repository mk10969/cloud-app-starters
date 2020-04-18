package org.uma.cloud.common.service.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.business.BusinessRacingHorse;
import org.uma.cloud.common.repository.business.BusinessRacingHorseRepository;

import java.util.List;

@Service
public class BusinessRacingHorseService {

    @Autowired
    private BusinessRacingHorseRepository repository;

    @Transactional
    public void update(BusinessRacingHorse businessRacingHorse) {
        repository.save(businessRacingHorse);
    }

    @Transactional
    public void updateAll(List<BusinessRacingHorse> model) {
        repository.saveAll(model);
    }

}
