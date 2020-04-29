package org.uma.cloud.common.service.odds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.odds.QuinellaPlace;
import org.uma.cloud.common.repository.odds.QuinellaPlaceRepository;

import java.util.List;

@Service
public class QuinellaPlaceService {

    @Autowired
    private QuinellaPlaceRepository repository;


    public QuinellaPlace findOne(String raceId) {
        return this.repository.findById(raceId).orElseThrow();
    }

    public boolean exists(String raceId) {
        return this.repository.existsById(raceId);
    }

    @Transactional
    public QuinellaPlace save(QuinellaPlace quinellaPlace) {
        return this.repository.save(quinellaPlace);
    }

    @Transactional
    public List<QuinellaPlace> saveAll(List<QuinellaPlace> quinellaPlaces) {
        return this.repository.saveAll(quinellaPlaces);
    }

    @Transactional
    public void delete(QuinellaPlace quinellaPlace) {
        this.repository.delete(quinellaPlace);
    }
}
