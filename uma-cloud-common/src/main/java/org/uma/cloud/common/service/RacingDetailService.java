package org.uma.cloud.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.entity.RacingDetail;
import org.uma.cloud.common.repository.RacingDetailRepository;

import java.util.List;

@Service
public class RacingDetailService {

    @Autowired
    private RacingDetailRepository repository;


    public RacingDetail findOne(String raceId) {
        return this.repository.findById(raceId).orElseThrow();
    }

    public boolean exists(String raceId) {
        return this.repository.existsById(raceId);
    }

    @Transactional
    public RacingDetail save(RacingDetail racingDetail) {
        return this.repository.save(racingDetail);
    }

    @Transactional
    public List<RacingDetail> saveAll(List<RacingDetail> racingDetails) {
        return this.repository.saveAll(racingDetails);
    }

    @Transactional
    public void delete(RacingDetail racingDetail) {
        this.repository.delete(racingDetail);
    }
}
