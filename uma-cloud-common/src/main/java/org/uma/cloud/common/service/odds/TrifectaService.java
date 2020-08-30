package org.uma.cloud.common.service.odds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.OddsTrifecta;
import org.uma.cloud.common.repository.odds.TrifectaRepository;

import java.util.List;

@Service
public class TrifectaService {

    @Autowired
    private TrifectaRepository repository;


    public OddsTrifecta findOne(String raceId) {
        return this.repository.findById(raceId).orElseThrow();
    }

    public boolean exists(String raceId) {
        return this.repository.existsById(raceId);
    }

    @Transactional
    public OddsTrifecta save(OddsTrifecta oddsTrifecta) {
        return this.repository.save(oddsTrifecta);
    }

    @Transactional
    public List<OddsTrifecta> saveAll(List<OddsTrifecta> oddsTrifectas) {
        return this.repository.saveAll(oddsTrifectas);
    }

    @Transactional
    public void delete(OddsTrifecta oddsTrifecta) {
        this.repository.delete(oddsTrifecta);
    }
}
