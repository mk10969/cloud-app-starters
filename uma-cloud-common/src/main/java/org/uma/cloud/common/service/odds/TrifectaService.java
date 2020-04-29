package org.uma.cloud.common.service.odds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.odds.Trifecta;
import org.uma.cloud.common.repository.odds.TrifectaRepository;

import java.util.List;

@Service
public class TrifectaService {

    @Autowired
    private TrifectaRepository repository;


    public Trifecta findOne(String raceId) {
        return this.repository.findById(raceId).orElseThrow();
    }

    public boolean exists(String raceId) {
        return this.repository.existsById(raceId);
    }

    @Transactional
    public Trifecta save(Trifecta trifecta) {
        return this.repository.save(trifecta);
    }

    @Transactional
    public List<Trifecta> saveAll(List<Trifecta> trifectas) {
        return this.repository.saveAll(trifectas);
    }

    @Transactional
    public void delete(Trifecta trifecta) {
        this.repository.delete(trifecta);
    }
}
