package org.uma.cloud.common.service.odds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.OddsTrio;
import org.uma.cloud.common.repository.odds.TrioRepository;

import java.util.List;

@Service
public class TrioService {

    @Autowired
    private TrioRepository repository;


    public OddsTrio findOne(String raceId) {
        return this.repository.findById(raceId).orElseThrow();
    }

    public boolean exists(String raceId) {
        return this.repository.existsById(raceId);
    }

    @Transactional
    public OddsTrio save(OddsTrio oddsTrio) {
        return this.repository.save(oddsTrio);
    }

    @Transactional
    public List<OddsTrio> saveAll(List<OddsTrio> oddsTrios) {
        return this.repository.saveAll(oddsTrios);
    }

    @Transactional
    public void delete(OddsTrio oddsTrio) {
        this.repository.delete(oddsTrio);
    }
}