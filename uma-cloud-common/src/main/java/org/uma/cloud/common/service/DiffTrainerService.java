package org.uma.cloud.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.entity.DiffTrainer;
import org.uma.cloud.common.repository.DiffTrainerRepository;

import java.util.List;

@Service
public class DiffTrainerService {

    @Autowired
    private DiffTrainerRepository repository;


    public DiffTrainer findOne(Integer trainerCd) {
        return this.repository.findById(trainerCd).orElseThrow();
    }

    public boolean exists(Integer trainerCd) {
        return this.repository.existsById(trainerCd);
    }

    @Transactional
    public DiffTrainer save(DiffTrainer diffTrainer) {
        return this.repository.save(diffTrainer);
    }

    @Transactional
    public List<DiffTrainer> saveAll(List<DiffTrainer> diffTrainers) {
        return this.repository.saveAll(diffTrainers);
    }

    @Transactional
    public void delete(DiffTrainer diffTrainer) {
        this.repository.delete(diffTrainer);
    }
}
