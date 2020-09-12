package org.uma.cloud.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.entity.BloodLine;
import org.uma.cloud.common.repository.BloodLineRepository;

import java.util.List;

@Service
public class BloodLineService {

    @Autowired
    private BloodLineRepository repository;


    public BloodLine findOne(Long bloodlineNo) {
        return this.repository.findById(bloodlineNo).orElseThrow();
    }

    public boolean exists(Long bloodlineNo) {
        return this.repository.existsById(bloodlineNo);
    }

    @Transactional
    public BloodLine save(BloodLine bloodLine) {
        return this.repository.save(bloodLine);
    }

    @Transactional
    public List<BloodLine> saveAll(List<BloodLine> bloodLines) {
        return this.repository.saveAll(bloodLines);
    }

    @Transactional
    public void delete(BloodLine bloodLine) {
        this.repository.delete(bloodLine);
    }
}