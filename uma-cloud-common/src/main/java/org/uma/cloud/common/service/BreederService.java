package org.uma.cloud.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.entity.DiffBreeder;
import org.uma.cloud.common.repository.BreederRepository;

import java.util.List;

@Service
public class BreederService {

    @Autowired
    private BreederRepository repository;


    public DiffBreeder findOne(Integer breederCd) {
        return this.repository.findById(breederCd).orElseThrow();
    }

    public boolean exists(Integer breederCd) {
        return this.repository.existsById(breederCd);
    }

    @Transactional
    public DiffBreeder save(DiffBreeder diffBreeder) {
        return this.repository.save(diffBreeder);
    }

    @Transactional
    public List<DiffBreeder> saveAll(List<DiffBreeder> diffBreeders) {
        return this.repository.saveAll(diffBreeders);
    }

    @Transactional
    public void delete(DiffBreeder diffBreeder) {
        this.repository.delete(diffBreeder);
    }
}
