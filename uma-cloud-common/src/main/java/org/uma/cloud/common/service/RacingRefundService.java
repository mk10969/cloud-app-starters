package org.uma.cloud.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.entity.RacingRefund;
import org.uma.cloud.common.repository.RacingRefundRepository;

import java.util.List;

@Service
public class RacingRefundService {

    @Autowired
    private RacingRefundRepository repository;


    public RacingRefund findOne(String raceId) {
        return this.repository.findById(raceId).orElseThrow();
    }

    public boolean exists(String raceId) {
        return this.repository.existsById(raceId);
    }

    @Transactional
    public RacingRefund save(RacingRefund racingRefund) {
        return this.repository.save(racingRefund);
    }

    @Transactional
    public List<RacingRefund> saveAll(List<RacingRefund> racingRefunds) {
        return this.repository.saveAll(racingRefunds);
    }

    @Transactional
    public void delete(RacingRefund racingRefund) {
        this.repository.delete(racingRefund);
    }
}
