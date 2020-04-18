package org.uma.cloud.common.service.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.business.BusinessRacing;
import org.uma.cloud.common.repository.business.BusinessRacingRepository;
import org.uma.cloud.common.utils.lang.DateUtil;

import java.util.List;
import java.util.function.Supplier;

@Service
public class BusinessRacingService {

    private static final Supplier<Long> now = System::currentTimeMillis;


    @Autowired
    private BusinessRacingRepository repository;


    public List<BusinessRacing> findComingRaces() {
        return repository.findByRaceStartDateTimeAfter(DateUtil.toLocalDateTime(now.get()));
    }

    @Transactional
    public void update(BusinessRacing model) {
        repository.save(model);
    }

    @Transactional
    public void updateAll(List<BusinessRacing> model) {
        repository.saveAll(model);
    }

    @Transactional
    public void delete(BusinessRacing model) {
        repository.delete(model);
    }

    @Transactional
    public void deleteByRaceId(String raceId) {
        repository.deleteById(raceId);
    }

}