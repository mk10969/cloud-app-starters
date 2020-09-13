package org.uma.cloud.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.entity.RacingVote;

public interface RacingVoteRepository extends JpaRepository<RacingVote, String> {
}
