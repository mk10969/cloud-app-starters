package org.uma.cloud.batch.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.uma.cloud.common.model.Jockey;


@Repository
public interface JockeyRepository extends CrudRepository<Jockey, Integer> {
}
