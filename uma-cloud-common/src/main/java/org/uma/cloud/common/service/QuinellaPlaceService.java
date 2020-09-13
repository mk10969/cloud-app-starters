package org.uma.cloud.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.entity.OddsQuinellaPlace;
import org.uma.cloud.common.repository.QuinellaPlaceRepository;

import java.util.List;

@Service
public class QuinellaPlaceService {

    @Autowired
    private QuinellaPlaceRepository repository;


    public OddsQuinellaPlace findOne(String raceId) {
        return this.repository.findById(raceId).orElseThrow();
    }

    public boolean exists(String raceId) {
        return this.repository.existsById(raceId);
    }

    @Transactional
    public OddsQuinellaPlace save(OddsQuinellaPlace oddsQuinellaPlace) {
        return this.repository.save(oddsQuinellaPlace);
    }

    @Transactional
    public List<OddsQuinellaPlace> saveAll(List<OddsQuinellaPlace> oddsQuinellaPlaces) {
        return this.repository.saveAll(oddsQuinellaPlaces);
    }

    @Transactional
    public void delete(OddsQuinellaPlace oddsQuinellaPlace) {
        this.repository.delete(oddsQuinellaPlace);
    }
}
