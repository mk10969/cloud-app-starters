package org.uma.cloud.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.BusinessModel;
import org.uma.cloud.common.repository.BusinessModelRepository;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class BusinessModelService {

    @Autowired
    private BusinessModelRepository businessModelRepository;


    public long getLatestBaseDate() {
        return this.businessModelRepository.findTopByOrderByIdDesc().getBaseDate();
    }

    public List<BusinessModel> findAll() {
        return businessModelRepository.findAll();
    }

    @Transactional
    public void insertBaseDate() {
        BusinessModel businessModel = new BusinessModel();
        businessModel.setBaseDate(ZonedDateTime.now().toInstant().getEpochSecond());
        this.businessModelRepository.save(businessModel);
    }

}
