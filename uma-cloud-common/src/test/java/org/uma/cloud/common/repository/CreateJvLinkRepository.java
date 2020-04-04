package org.uma.cloud.common.repository;


import org.junit.jupiter.api.Test;
import org.uma.cloud.common.ReflectionUtils;

public class CreateJvLinkRepository {

    @Test
    void test() {
        ReflectionUtils.getClassesFrom("org.uma.cloud.common.model").stream()
                .forEach(i -> System.out.println(i.getSimpleName()));


    }

}
