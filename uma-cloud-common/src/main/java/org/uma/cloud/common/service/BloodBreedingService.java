package org.uma.cloud.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.BloodBreeding;
import org.uma.cloud.common.repository.BloodBreedingRepository;

import java.util.List;

@Service
public class BloodBreedingService {

    @Autowired
    private BloodBreedingRepository repository;


    public BloodBreeding findOne(Integer breedingNo) {
        return this.repository.findById(breedingNo).orElseThrow();
    }

    public boolean exists(Integer breedingNo) {
        return this.repository.existsById(breedingNo);
    }

    @Transactional
    public BloodBreeding save(BloodBreeding bloodBreeding) {
        return this.repository.save(bloodBreeding);
    }

    @Transactional
    public List<BloodBreeding> saveAll(List<BloodBreeding> bloodBreedings) {
        return this.repository.saveAll(bloodBreedings);
    }

    @Transactional
    public void delete(BloodBreeding bloodBreeding) {
        this.repository.delete(bloodBreeding);
    }
}
