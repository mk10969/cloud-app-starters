package org.uma.cloud.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.entity.RacingHorseExclusion;
import org.uma.cloud.common.repository.RacingHorseExclusionRepository;

import java.util.List;

@Service
public class RacingHorseExclusionService {

    @Autowired
    private RacingHorseExclusionRepository repository;


    public RacingHorseExclusion findOne(RacingHorseExclusion.CompositeId compositeId) {
        return this.repository.findById(compositeId).orElseThrow();
    }

    public boolean exists(RacingHorseExclusion.CompositeId compositeId) {
        return this.repository.existsById(compositeId);
    }

    @Transactional
    public RacingHorseExclusion save(RacingHorseExclusion racingHorseExclusion) {
        return this.repository.save(racingHorseExclusion);
    }

    @Transactional
    public List<RacingHorseExclusion> saveAll(List<RacingHorseExclusion> racingHorseExclusions) {
        return this.repository.saveAll(racingHorseExclusions);
    }

    @Transactional
    public void delete(RacingHorseExclusion racingHorseExclusion) {
        this.repository.delete(racingHorseExclusion);
    }
}
