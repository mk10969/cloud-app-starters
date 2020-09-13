package org.uma.cloud.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.entity.OddsExacta;
import org.uma.cloud.common.repository.ExactaRepository;

import java.util.List;

@Service
public class ExactaService {

    @Autowired
    private ExactaRepository repository;


    public OddsExacta findOne(String raceId) {
        return this.repository.findById(raceId).orElseThrow();
    }

    public boolean exists(String raceId) {
        return this.repository.existsById(raceId);
    }

    @Transactional
    public OddsExacta save(OddsExacta oddsExacta) {
        return this.repository.save(oddsExacta);
    }

    @Transactional
    public List<OddsExacta> saveAll(List<OddsExacta> oddsExactas) {
        return this.repository.saveAll(oddsExactas);
    }

    @Transactional
    public void delete(OddsExacta oddsExacta) {
        this.repository.delete(oddsExacta);
    }
}
