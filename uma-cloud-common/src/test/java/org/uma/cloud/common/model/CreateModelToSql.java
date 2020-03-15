package org.uma.cloud.common.model;

import com.google.common.base.CaseFormat;
import org.junit.jupiter.api.Test;
import org.uma.cloud.common.ReflectionUtils;
import org.uma.cloud.common.utils.javatuples.Pair;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateModelToSql {

    @Test
    void test() {
        assertEquals(1, 1);
    }

    @Test
    void test_model_to_sql() {

        ReflectionUtils.getClassesFrom("org.uma.cloud.common.model")
                .stream()
//                .map(this::once)
                .flatMap(clazz -> Stream.of(clazz.getDeclaredFields())
                        .map(Field::getGenericType))
                .map(Type::getTypeName)
                .sorted()
                .distinct()
                .forEach(System.out::println);

//                .flatMap(clazz -> Stream.of(clazz.getDeclaredFields())
//                        .map(j -> Triplet.with(clazz.getSimpleName(),
//                                j.getName(),
//                                j.getGenericType())))
//                .collect(Collectors.groupingBy(Triplet::getValue1));

    }


    private Map<String, List<Pair<String, Type>>> once(Class<?> clazz) {
        List<Pair<String, Type>> pairs = Stream.of(clazz.getDeclaredFields())
                .map(j -> {
                    String name = CaseFormat.UPPER_CAMEL
                            .to(CaseFormat.LOWER_UNDERSCORE, j.getName());
                    return Pair.with(name, j.getGenericType());
                })
                .collect(Collectors.toList());

        return Map.of(clazz.getSimpleName(), pairs);
    }


    @Test
    void test_type() {

    }


    public enum Mapper {

        Boolean("boolean"),
        Integer("integer"),
        Long("bigint"),
        String("varchar"),
        // numeric == decimal 違いはないらしい。
        BigDecimal("numeric"),
        LocalDate("date"),
        LocalTime("time"),
        ListInteger("integer[]"),
        ListLocalTime("time[]"),
        other("jsonb")
        ;
        private String type;

        Mapper(java.lang.String type) {
            this.type = type;
        }
    }


    @Test
    void testtest() {
        String a = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "TomatoCurry");
        System.out.println(a);
    }


}