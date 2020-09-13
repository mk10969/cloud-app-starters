package org.uma.cloud.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.entity.BloodAncestry;
import org.uma.cloud.common.repository.BloodAncestryRepository;

import java.util.List;

@Service
public class BloodAncestryService {

    @Autowired
    private BloodAncestryRepository repository;


    public BloodAncestry findOne(Integer breedingNo) {
        return this.repository.findById(breedingNo).orElseThrow();
    }

    public boolean exists(Integer breedingNo) {
        return this.repository.existsById(breedingNo);
    }

    @Transactional
    public BloodAncestry save(BloodAncestry bloodAncestry) {
        return this.repository.save(bloodAncestry);
    }

    @Transactional
    public List<BloodAncestry> saveAll(List<BloodAncestry> bloodAncestries) {
        return this.repository.saveAll(bloodAncestries);
    }

    @Transactional
    public void delete(BloodAncestry bloodAncestry) {
        this.repository.delete(bloodAncestry);
    }
}