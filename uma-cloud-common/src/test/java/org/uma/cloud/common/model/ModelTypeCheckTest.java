package org.uma.cloud.common.model;

import org.junit.jupiter.api.Test;
import org.uma.cloud.common.ReflectionUtils;
import org.uma.cloud.common.utils.javatuples.Pair;

import java.util.stream.Stream;

public class ModelTypeCheckTest {

    @Test
    void test_type_check() {
        ReflectionUtils.getClassesFrom("org.uma.cloud.common.model")
                .stream()
                .flatMap(clazz -> Stream.of(clazz.getDeclaredFields())
                        .map(i -> Pair.with(i.getName(), i.getGenericType())))
                .filter(pair -> pair.getValue2().getTypeName().contains("LocalTime"))
//                .map(Type::getTypeName)
//                .sorted()
//                .distinct()
                .forEach(System.out::println);
    }

}
