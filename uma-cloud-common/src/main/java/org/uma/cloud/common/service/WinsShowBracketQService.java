package org.uma.cloud.common.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.entity.OddsWinsShowBracketQ;
import org.uma.cloud.common.repository.WinsShowBracketQRepository;

import java.util.List;

@Service
public class WinsShowBracketQService {

    @Autowired
    private WinsShowBracketQRepository repository;


    public OddsWinsShowBracketQ findOne(String raceId) {
        return this.repository.findById(raceId).orElseThrow();
    }

    public boolean exists(String raceId) {
        return this.repository.existsById(raceId);
    }

    @Transactional
    public OddsWinsShowBracketQ save(OddsWinsShowBracketQ oddsWinsShowBracketQ) {
        return this.repository.save(oddsWinsShowBracketQ);
    }

    @Transactional
    public List<OddsWinsShowBracketQ> saveAll(List<OddsWinsShowBracketQ> oddsWinsShowBracketQS) {
        return this.repository.saveAll(oddsWinsShowBracketQS);
    }

    @Transactional
    public void delete(OddsWinsShowBracketQ oddsWinsShowBracketQ) {
        this.repository.delete(oddsWinsShowBracketQ);
    }
}