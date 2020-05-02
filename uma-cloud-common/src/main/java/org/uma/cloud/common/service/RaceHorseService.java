package org.uma.cloud.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.RaceHorse;
import org.uma.cloud.common.repository.RaceHorseRepository;

import java.util.List;

@Service
public class RaceHorseService {

    @Autowired
    private RaceHorseRepository repository;


    public RaceHorse findOne(RaceHorse.CompositeId compositeId) {
        return this.repository.findById(compositeId).orElseThrow();
    }

    public boolean exists(RaceHorse.CompositeId compositeId) {
        return this.repository.existsById(compositeId);
    }

    @Transactional
    public RaceHorse save(RaceHorse raceHorse) {
        return this.repository.save(raceHorse);
    }

    @Transactional
    public List<RaceHorse> saveAll(List<RaceHorse> raceHorses) {
        return this.repository.saveAll(raceHorses);
    }

    @Transactional
    public void delete(RaceHorse raceHorse) {
        this.repository.delete(raceHorse);
    }
}
