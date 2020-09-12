package org.uma.cloud.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.entity.OddsQuinella;
import org.uma.cloud.common.repository.QuinellaRepository;

import java.util.List;

@Service
public class QuinellaService {

    @Autowired
    private QuinellaRepository repository;


    public OddsQuinella findOne(String raceId) {
        return this.repository.findById(raceId).orElseThrow();
    }

    public boolean exists(String raceId) {
        return this.repository.existsById(raceId);
    }

    @Transactional
    public OddsQuinella save(OddsQuinella oddsQuinella) {
        return this.repository.save(oddsQuinella);
    }

    @Transactional
    public List<OddsQuinella> saveAll(List<OddsQuinella> oddsQuinellas) {
        return this.repository.saveAll(oddsQuinellas);
    }

    @Transactional
    public void delete(OddsQuinella oddsQuinella) {
        this.repository.delete(oddsQuinella);
    }
}