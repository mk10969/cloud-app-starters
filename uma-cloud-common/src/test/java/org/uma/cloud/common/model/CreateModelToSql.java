package org.uma.cloud.common.model;

import com.google.common.base.CaseFormat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.uma.cloud.common.BaseSpringBootTest;
import org.uma.cloud.common.ReflectionUtils;
import org.uma.cloud.common.configuration.JvLinkRecordProperties;
import org.uma.cloud.common.utils.javatuples.Pair;
import org.uma.cloud.common.utils.javatuples.Triplet;

import java.util.Map;
import java.util.stream.Stream;


class CreateModelToSql extends BaseSpringBootTest {

    @Autowired
    private Map<String, JvLinkRecordProperties.RecordSpecItems> recordSpecs;


    @Test
    void test_モデルからCREATE_SQLを出力する() {
        ReflectionUtils.getClassesFrom("org.uma.cloud.common.model")
                .stream()
                .map(clazz -> Stream.of(clazz.getDeclaredFields())
                        .map(field -> {
                            Integer length = recordSpecs.get("RA").getRecordItems().stream()
                                    .filter(j -> j.getColumn().equals(field.getName()))
                                    .map(JvLinkRecordProperties.RecordSpecItems.RecordItem::getLength)
                                    .findFirst().orElse(null);

                            String name = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());
                            Mapper mapper = Mapper.mapper(field.getGenericType().getTypeName());
                            return Triplet.with(name, mapper, length);
                        })
                        .map(triplet -> {
                            if (triplet.getValue2() == Mapper.String
                                    && !triplet.getValue2().toString().contains("(15)")) {
                                return "   " + triplet.getValue1() + "     " + triplet.getValue2().getType() + "(" + triplet.getValue3() + ")";
                            } else {
                                return "   " + triplet.getValue1() + "     " + triplet.getValue2().getType();
                            }
                        })
                        .reduce(clazz.getSimpleName(), (i, j) -> i + ",\n" + j))
                .forEach(System.out::println);
    }

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


    @Test
    void test_モデルクラス名をスネーク化() {
        ReflectionUtils.getClassesFrom("org.uma.cloud.common.model")
                .stream()
                .map(Class::getSimpleName)
                .map(i -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, i))
                .forEach(System.out::println);
    }

    @Test
    void tst_Enumのフィールドとってきて文字の長さ調べる() {
        String name = ReflectionUtils.getClassesFrom("org.uma.cloud.common.code").stream()
                .flatMap(clazz -> Stream.of(clazz.getEnumConstants()))
                .map(Object::toString)
                .peek(System.out::println)
                .reduce("", (i, j) -> i.length() > j.length() ? i : j);
        System.out.println(name + ":" + name.length());

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
        // 考えるのめんどいの一律で、max 15
        Code("varchar(15)"),
        other("jsonb");

        public String getType() {
            return this.type;
        }

        private String type;

        Mapper(java.lang.String type) {
            this.type = type;
        }

        public static Mapper mapper(String typeName) {
            if ("java.lang.String".equals(typeName)) {
                return String;
            } else if ("java.lang.Integer".equals(typeName)) {
                return Integer;
            } else if ("java.lang.Boolean".equals(typeName)) {
                return Boolean;
            } else if ("java.lang.Long".equals(typeName)) {
                return Long;
            } else if ("java.math.BigDecimal".equals(typeName)) {
                return BigDecimal;
            } else if ("java.time.LocalDate".equals(typeName)) {
                return LocalDate;
            } else if ("java.time.LocalTime".equals(typeName)) {
                return LocalTime;
            } else if (typeName.startsWith("org.uma.cloud.common.code")) {
                return Code;
            } else if ("java.util.List<java.lang.Integer>".equals(typeName)) {
                return ListInteger;
            } else if ("java.util.List<java.time.LocalTime>".equals(typeName)) {
                return ListLocalTime;
            } else {
                return other;
            }
        }

    }


    @Test
    void test_LOWER_UNDERSCORE() {
        String a = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "TomatoCurry");
        System.out.println(a);
    }


}