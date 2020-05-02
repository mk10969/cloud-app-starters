package org.uma.cloud.stream.type;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.utils.exception.JvLinkModelMappingException;
import org.uma.cloud.common.utils.exception.JvLinkModelNullPointException;
import reactor.core.publisher.Mono;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class JpaEntitySink {

    @Autowired
    private EntityManager entityManager;


    @Transactional
    public <S> List<S> saveAll(List<S> entities) {
        List<S> result = new ArrayList<>();
        for (S entity : entities) {
            result.add(save(entity));
        }
        return result;
    }

    @Transactional
    public <S> S save(S entity) {
        // persist Only
        entityManager.persist(entity);
        return entity;
    }

    public <S> boolean notExists(S entity, Object id) {
        if (entityManager.find(entity.getClass(), id) == null) {
            // 存在しない
            return true;
        } else {
            // 存在する。
            log.warn("Already exists data: {}", entity);
            return false;
        }
    }

    public <S> Mono<S> errorResume(Throwable throwable) {
        if (throwable instanceof JvLinkModelMappingException) {
            log.warn("Mapping Error data: {}", ((JvLinkModelMappingException) throwable).getLineData());

        } else if (throwable instanceof JvLinkModelNullPointException) {
            log.warn("Field NullPo data: {}", ((JvLinkModelNullPointException) throwable).getLineData());

        } else if (throwable instanceof PersistenceException) {
            log.error("Persist Error:", throwable);

        } else {
            log.error("Error:", throwable);
        }
        return Mono.empty();
    }
}
