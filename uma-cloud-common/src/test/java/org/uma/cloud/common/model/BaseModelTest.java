package org.uma.cloud.common.model;

import org.junit.jupiter.api.Test;
import org.uma.cloud.common.utils.javatuples.Triplet;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.uma.cloud.common.ReflectionUtils.getClassesFrom;

class BaseModelTest {


    @Test
    void annotaion確認() {
        Set<Class<?>> modelClazz = getClassesFrom("org.uma.cloud.common.model");
        modelClazz.stream()
                .filter(i -> !"BaseModel".equals(i.getSimpleName()))
                .flatMap(model -> Arrays.stream(model.getDeclaredFields()))
                .filter(field -> !field.getType().getSimpleName().equals("String"))
                .filter(field -> !field.getType().getSimpleName().equals("Integer"))
                .filter(field -> !field.getType().getSimpleName().equals("Long"))
                .filter(field -> !field.getType().getSimpleName().equals("LocalDate"))
                .filter(field -> !field.getType().getSimpleName().equals("Boolean"))
                .filter(field -> !field.getType().getSimpleName().equals("Double"))
                .map(field -> {
                    Class<?> fieldType = field.getType();
                    String fieldName = field.getName();
                    List<Annotation> annotations = Arrays.stream(field.getAnnotations())
                            .collect(Collectors.toList());
                    return Triplet.with(fieldName, fieldType, annotations);
                })
                .forEach(System.out::println);
    }

    @Test
    void test_model一覧取得() {
        Set<Class<?>> modelClazz = getClassesFrom("org.uma.cloud.common.model");
        modelClazz.stream()
                .map(Class::getSimpleName)
                .forEach(System.out::println);

    }


}