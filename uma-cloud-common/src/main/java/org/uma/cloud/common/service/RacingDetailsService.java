package org.uma.cloud.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.RacingDetail;
import org.uma.cloud.common.repository.RacingDetailRepository;

import java.util.List;

@Service
public class RacingDetailsService {

    @Autowired
    private RacingDetailRepository repository;


    public RacingDetail findOne(String raceId) {
        return repository.findById(raceId).orElseThrow(() ->
                new IllegalArgumentException(raceId + " のデータが見つかりませんでした。"));
    }

    public List<RacingDetail> findAllByRaceId(List<String> raceIds) {
        return repository.findAllById(raceIds);
    }

    @Transactional
    public void save(RacingDetail racingDetail) {
        repository.save(racingDetail);
    }

    @Transactional
    public void saveAll(List<RacingDetail> racingDetails) {
        repository.saveAll(racingDetails);
    }

    @Transactional
    public void delete(RacingDetail racingDetail) {
        repository.delete(racingDetail);
    }

}
