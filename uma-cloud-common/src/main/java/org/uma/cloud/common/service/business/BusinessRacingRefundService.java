package org.uma.cloud.common.service.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.business.BusinessRacingRefund;
import org.uma.cloud.common.repository.business.BusinessRacingRefundRepository;

@Service
public class BusinessRacingRefundService {

    @Autowired
    private BusinessRacingRefundRepository repository;

    @Transactional
    public BusinessRacingRefund update(BusinessRacingRefund businessRacingRefund) {
        return repository.save(businessRacingRefund);
    }

}
