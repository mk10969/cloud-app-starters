package org.uma.cloud.common.service.odds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.odds.Quinella;
import org.uma.cloud.common.repository.odds.QuinellaRepository;

import java.util.List;

@Service
public class QuinellaService {

    @Autowired
    private QuinellaRepository repository;


    public Quinella findOne(String raceId) {
        return this.repository.findById(raceId).orElseThrow();
    }

    public boolean exists(String raceId) {
        return this.repository.existsById(raceId);
    }

    @Transactional
    public Quinella save(Quinella quinella) {
        return this.repository.save(quinella);
    }

    @Transactional
    public List<Quinella> saveAll(List<Quinella> quinellas) {
        return this.repository.saveAll(quinellas);
    }

    @Transactional
    public void delete(Quinella quinella) {
        this.repository.delete(quinella);
    }
}