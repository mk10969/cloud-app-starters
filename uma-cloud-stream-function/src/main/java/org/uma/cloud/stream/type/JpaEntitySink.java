package org.uma.cloud.stream.type;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.entity.BaseModel;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class JpaEntitySink {

    @Autowired
    private EntityManager entityManager;


    @Transactional
    public <S> List<S> persistAll(List<S> entities) {
        List<S> result = new ArrayList<>();
        for (S entity : entities) {
            result.add(persist(entity));
        }
        return result;
    }

    @Transactional
    public <S> S persist(S entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    public <S> List<S> mergeAll(List<S> entities) {
        List<S> result = new ArrayList<>();
        for (S entity : entities) {
            result.add(merge(entity));
        }
        return result;
    }

    @Transactional
    public <S> S merge(S entity) {
        entityManager.merge(entity);
        return entity;
    }


    public <S extends BaseModel> boolean notExists(S entity, Object id) {
        if (entityManager.find(entity.getClass(), id) == null) {
            // 存在しない
            return true;
        } else {
            // 存在する。
            log.warn("Already exists data: {}", entity.toJson());
            return false;
        }
    }

    public <S extends BaseModel> void logAlreadyExists(S entity, Object id) {
        S exist = (S) entityManager.find(entity.getClass(), id);
        if (exist != null) {
            // 存在する。
            try {
                // 一致した場合、無視。
                // しなかった場合のみ、どこに差分があるか抽出する。
                JSONAssert.assertEquals(exist.toJson(), entity.toJson(), JSONCompareMode.STRICT);
            } catch (AssertionError | JSONException e) {
                log.warn("diff: " + e.getMessage());
                // 念のため。どっちもログに、、
                log.warn("db: " + exist.toJson());
                log.warn("web: " + entity.toJson());
            }
        }
    }

}
