package org.uma.cloud.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.RacingDetails;
import org.uma.cloud.common.repository.RacingDetailsRepository;

import java.util.List;

@Service
public class RacingDetailsService {

    @Autowired
    private RacingDetailsRepository repository;


    public RacingDetails findOne(String raceId) {
        return repository.findById(raceId).orElseThrow(() ->
                new IllegalArgumentException(raceId + " のデータが見つかりませんでした。"));
    }

    public List<RacingDetails> findAllByRaceId(List<String> raceIds) {
        return repository.findAllById(raceIds);
    }

    @Transactional
    public void save(RacingDetails racingDetails) {
        repository.save(racingDetails);
    }

    @Transactional
    public void saveAll(List<RacingDetails> racingDetails) {
        repository.saveAll(racingDetails);
    }

    @Transactional
    public void delete(RacingDetails racingDetails) {
        repository.delete(racingDetails);
    }

}
