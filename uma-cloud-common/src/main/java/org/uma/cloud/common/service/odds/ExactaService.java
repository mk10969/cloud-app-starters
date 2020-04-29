package org.uma.cloud.common.service.odds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.odds.Exacta;
import org.uma.cloud.common.repository.odds.ExactaRepository;

import java.util.List;

@Service
public class ExactaService {

    @Autowired
    private ExactaRepository repository;


    public Exacta findOne(String raceId) {
        return this.repository.findById(raceId).orElseThrow();
    }

    public boolean exists(String raceId) {
        return this.repository.existsById(raceId);
    }

    @Transactional
    public Exacta save(Exacta exacta) {
        return this.repository.save(exacta);
    }

    @Transactional
    public List<Exacta> saveAll(List<Exacta> exactas) {
        return this.repository.saveAll(exactas);
    }

    @Transactional
    public void delete(Exacta exacta) {
        this.repository.delete(exacta);
    }
}
