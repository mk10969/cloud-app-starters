package org.uma.cloud.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uma.cloud.common.entity.RacingOdds;
import org.uma.cloud.common.repository.RacingOddsRepository;

@Service
public class RacingOddsService {

    @Autowired
    private RacingOddsRepository repository;

    public RacingOdds findOne(RacingOdds.CompositeId compositeId) {
        return this.repository.findById(compositeId).orElseThrow();
    }
}
