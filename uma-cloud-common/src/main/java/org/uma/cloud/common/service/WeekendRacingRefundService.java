package org.uma.cloud.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.entity.WeekendRacingRefund;
import org.uma.cloud.common.repository.WeekendRacingRefundRepository;

@Service
public class WeekendRacingRefundService {

    @Autowired
    private WeekendRacingRefundRepository repository;

    @Transactional
    public WeekendRacingRefund update(WeekendRacingRefund weekendRacingRefund) {
        return repository.save(weekendRacingRefund);
    }

}
