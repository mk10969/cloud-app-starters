package org.uma.cloud.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.DiffJockey;
import org.uma.cloud.common.repository.JockeyRepository;

import java.util.List;

@Service
public class JockeyService {

    @Autowired
    private JockeyRepository repository;


    public DiffJockey findOne(Integer jockeyCd) {
        return this.repository.findById(jockeyCd).orElseThrow();
    }

    public boolean exists(Integer jockeyCd) {
        return this.repository.existsById(jockeyCd);
    }

    @Transactional
    public DiffJockey save(DiffJockey diffJockey) {
        return this.repository.save(diffJockey);
    }

    @Transactional
    public List<DiffJockey> saveAll(List<DiffJockey> diffJockeys) {
        return this.repository.saveAll(diffJockeys);
    }

    @Transactional
    public void delete(DiffJockey diffJockey) {
        this.repository.delete(diffJockey);
    }
}