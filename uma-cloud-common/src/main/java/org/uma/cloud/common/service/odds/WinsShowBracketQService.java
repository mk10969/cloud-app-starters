package org.uma.cloud.common.service.odds;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.odds.WinsShowBracketQ;
import org.uma.cloud.common.repository.odds.WinsShowBracketQRepository;

import java.util.List;

@Service
public class WinsShowBracketQService {

    @Autowired
    private WinsShowBracketQRepository repository;


    public WinsShowBracketQ findOne(String raceId) {
        return this.repository.findById(raceId).orElseThrow();
    }

    public boolean exists(String raceId) {
        return this.repository.existsById(raceId);
    }

    @Transactional
    public WinsShowBracketQ save(WinsShowBracketQ winsShowBracketQ) {
        return this.repository.save(winsShowBracketQ);
    }

    @Transactional
    public List<WinsShowBracketQ> saveAll(List<WinsShowBracketQ> winsShowBracketQs) {
        return this.repository.saveAll(winsShowBracketQs);
    }

    @Transactional
    public void delete(WinsShowBracketQ winsShowBracketQ) {
        this.repository.delete(winsShowBracketQ);
    }
}