package org.uma.cloud.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.Trainer;
import org.uma.cloud.common.repository.TrainerRepository;

import java.util.List;

@Service
public class TrainerService {

    @Autowired
    private TrainerRepository repository;


    public Trainer findOne(Integer trainerCd) {
        return this.repository.findById(trainerCd).orElseThrow();
    }

    public boolean exists(Integer trainerCd) {
        return this.repository.existsById(trainerCd);
    }

    @Transactional
    public Trainer save(Trainer trainer) {
        return this.repository.save(trainer);
    }

    @Transactional
    public List<Trainer> saveAll(List<Trainer> trainers) {
        return this.repository.saveAll(trainers);
    }

    @Transactional
    public void delete(Trainer trainer) {
        this.repository.delete(trainer);
    }
}
