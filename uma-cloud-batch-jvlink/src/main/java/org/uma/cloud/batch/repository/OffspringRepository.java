package org.uma.cloud.batch.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.uma.cloud.common.model.Offspring;


@Repository
public interface OffspringRepository extends CrudRepository<Offspring, Long> {
}
