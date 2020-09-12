package org.uma.cloud.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.entity.RacingHorseDetail;
import org.uma.cloud.common.repository.RacingHorseDetailRepository;

import java.util.List;

@Service
public class RacingHorseDetailService {

    @Autowired
    private RacingHorseDetailRepository repository;


    public RacingHorseDetail findOne(RacingHorseDetail.CompositeId compositeId) {
        return this.repository.findById(compositeId).orElseThrow();
    }

    public boolean exists(RacingHorseDetail.CompositeId compositeId) {
        return this.repository.existsById(compositeId);
    }

    @Transactional
    public RacingHorseDetail save(RacingHorseDetail racingHorseDetail) {
        return this.repository.save(racingHorseDetail);
    }

    @Transactional
    public List<RacingHorseDetail> saveAll(List<RacingHorseDetail> racingHorseDetails) {
        return this.repository.saveAll(racingHorseDetails);
    }

    @Transactional
    public void delete(RacingHorseDetail racingHorseDetail) {
        this.repository.delete(racingHorseDetail);
    }
}
