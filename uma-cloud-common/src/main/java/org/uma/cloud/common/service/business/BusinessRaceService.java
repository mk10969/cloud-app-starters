package org.uma.cloud.common.service.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.business.BusinessRace;
import org.uma.cloud.common.repository.business.BusinessRaceRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

@Service
public class BusinessRaceService {

    private static final Supplier<LocalDateTime> now = LocalDateTime::now;


    @Autowired
    private BusinessRaceRepository repository;


    public List<BusinessRace> findCommingRaces() {
        return repository.findByRaceStartDateTime(now.get());
    }

    @Transactional
    public void update(BusinessRace model) {
        repository.save(model);
    }

    @Transactional
    public void updateAll(List<BusinessRace> model) {
        repository.saveAll(model);
    }

    @Transactional
    public void delete(BusinessRace model) {
        repository.delete(model);
    }

    @Transactional
    public void deleteByRaceId(String raceId) {
        repository.deleteById(raceId);
    }

}
