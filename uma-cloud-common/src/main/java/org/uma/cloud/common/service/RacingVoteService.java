package org.uma.cloud.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.RacingVote;
import org.uma.cloud.common.repository.RacingVoteRepository;

import java.util.List;

@Service
public class RacingVoteService {

    @Autowired
    private RacingVoteRepository repository;


    public RacingVote findOne(String raceId) {
        return this.repository.findById(raceId).orElseThrow();
    }

    public boolean exists(String raceId) {
        return this.repository.existsById(raceId);
    }

    @Transactional
    public RacingVote save(RacingVote racingVote) {
        return this.repository.save(racingVote);
    }

    @Transactional
    public List<RacingVote> saveAll(List<RacingVote> racingVotes) {
        return this.repository.saveAll(racingVotes);
    }

    @Transactional
    public void delete(RacingVote racingVote) {
        this.repository.delete(racingVote);
    }
}
