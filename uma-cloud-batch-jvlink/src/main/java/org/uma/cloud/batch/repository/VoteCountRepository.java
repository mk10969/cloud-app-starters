package org.uma.cloud.batch.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.uma.cloud.common.model.VoteCount;


@Repository
public interface VoteCountRepository extends CrudRepository<VoteCount, String> {
}
