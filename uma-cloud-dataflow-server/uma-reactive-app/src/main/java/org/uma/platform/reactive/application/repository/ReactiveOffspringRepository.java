package org.uma.platform.reactive.application.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import org.uma.platform.common.model.Offspring;

@Repository
public interface ReactiveOffspringRepository extends ReactiveMongoRepository<Offspring, String> {

}
