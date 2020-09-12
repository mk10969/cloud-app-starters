package org.uma.cloud.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.entity.DiffRaceHorse;
import org.uma.cloud.common.repository.RaceHorseRepository;

import java.util.List;

@Service
public class RaceHorseService {

    @Autowired
    private RaceHorseRepository repository;


    public DiffRaceHorse findOne(DiffRaceHorse.CompositeId compositeId) {
        return this.repository.findById(compositeId).orElseThrow();
    }

    public boolean exists(DiffRaceHorse.CompositeId compositeId) {
        return this.repository.existsById(compositeId);
    }

    @Transactional
    public DiffRaceHorse save(DiffRaceHorse diffRaceHorse) {
        return this.repository.save(diffRaceHorse);
    }

    @Transactional
    public List<DiffRaceHorse> saveAll(List<DiffRaceHorse> diffRaceHors) {
        return this.repository.saveAll(diffRaceHors);
    }

    @Transactional
    public void delete(DiffRaceHorse diffRaceHorse) {
        this.repository.delete(diffRaceHorse);
    }
}
