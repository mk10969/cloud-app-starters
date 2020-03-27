package org.uma.cloud.batch.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.uma.cloud.common.model.Ancestry;


@Repository
public interface AncestryRepository extends CrudRepository<Ancestry, Integer> {
}
