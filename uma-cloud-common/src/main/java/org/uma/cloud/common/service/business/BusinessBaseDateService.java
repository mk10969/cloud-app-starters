package org.uma.cloud.common.service.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.business.BusinessBaseDate;
import org.uma.cloud.common.repository.business.BusinessBaseDateRepository;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class BusinessBaseDateService {

    @Autowired
    private BusinessBaseDateRepository repository;


    public long getLatestBaseDate() {
        return this.repository.findTopByOrderByIdDesc().getBaseDate();
    }

    public List<BusinessBaseDate> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void insertBaseDate() {
        BusinessBaseDate businessBaseDate = new BusinessBaseDate();
        businessBaseDate.setBaseDate(ZonedDateTime.now().toInstant().getEpochSecond());
        this.repository.save(businessBaseDate);
    }

}
