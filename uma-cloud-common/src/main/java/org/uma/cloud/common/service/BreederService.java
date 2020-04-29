package org.uma.cloud.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.Breeder;
import org.uma.cloud.common.repository.BreederRepository;

import java.util.List;

@Service
public class BreederService {

    @Autowired
    private BreederRepository repository;


    public Breeder findOne(Integer breederCd) {
        return this.repository.findById(breederCd).orElseThrow();
    }

    public boolean exists(Integer breederCd) {
        return this.repository.existsById(breederCd);
    }

    @Transactional
    public Breeder save(Breeder breeder) {
        return this.repository.save(breeder);
    }

    @Transactional
    public List<Breeder> saveAll(List<Breeder> breeders) {
        return this.repository.saveAll(breeders);
    }

    @Transactional
    public void delete(Breeder breeder) {
        this.repository.delete(breeder);
    }
}
