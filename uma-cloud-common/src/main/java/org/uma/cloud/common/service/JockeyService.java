package org.uma.cloud.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.Jockey;
import org.uma.cloud.common.repository.JockeyRepository;

import java.util.List;

@Service
public class JockeyService {

    @Autowired
    private JockeyRepository repository;


    public Jockey findOne(Integer jockeyCd) {
        return this.repository.findById(jockeyCd).orElseThrow();
    }

    public boolean exists(Integer jockeyCd) {
        return this.repository.existsById(jockeyCd);
    }

    @Transactional
    public Jockey save(Jockey jockey) {
        return this.repository.save(jockey);
    }

    @Transactional
    public List<Jockey> saveAll(List<Jockey> jockeys) {
        return this.repository.saveAll(jockeys);
    }

    @Transactional
    public void delete(Jockey jockey) {
        this.repository.delete(jockey);
    }
}