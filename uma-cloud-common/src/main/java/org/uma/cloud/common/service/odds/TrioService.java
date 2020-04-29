package org.uma.cloud.common.service.odds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.odds.Trio;
import org.uma.cloud.common.repository.odds.TrioRepository;

import java.util.List;

@Service
public class TrioService {

    @Autowired
    private TrioRepository repository;


    public Trio findOne(String raceId) {
        return this.repository.findById(raceId).orElseThrow();
    }

    public boolean exists(String raceId) {
        return this.repository.existsById(raceId);
    }

    @Transactional
    public Trio save(Trio trio) {
        return this.repository.save(trio);
    }

    @Transactional
    public List<Trio> saveAll(List<Trio> trios) {
        return this.repository.saveAll(trios);
    }

    @Transactional
    public void delete(Trio trio) {
        this.repository.delete(trio);
    }
}