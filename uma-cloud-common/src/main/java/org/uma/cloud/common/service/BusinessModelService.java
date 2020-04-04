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
    private BusinessModelRepository repository;


    public long getLatestBaseDate() {
        return this.repository.findTopByOrderByIdDesc().getBaseDate();
    }

    public List<BusinessModel> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void insertBaseDate() {
        BusinessModel businessModel = new BusinessModel();
        businessModel.setBaseDate(ZonedDateTime.now().toInstant().getEpochSecond());
        this.repository.save(businessModel);
    }

}
