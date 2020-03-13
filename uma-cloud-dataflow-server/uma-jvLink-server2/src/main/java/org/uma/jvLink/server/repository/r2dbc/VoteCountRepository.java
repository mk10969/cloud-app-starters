package org.uma.cloud.job.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uma.cloud.common.model.VoteCount;

public interface VoteCountRepository extends JpaRepository<VoteCount, String> {
}
