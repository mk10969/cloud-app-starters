package org.uma.cloud.stream.processor.configuration;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.util.HashMap;
import java.util.Map;


class JvLinkClientModelMapperConfigurationTest {

    private final ModelMapper modelMapper = new ModelMapper();

    private static final Converter<String, Integer> toInteger = new AbstractConverter<String, Integer>() {
        @Override
        protected Integer convert(String source) {
            if ("  ".equals(source) || "   ".equals(source)) {
                return null;
            }
            if ("--".equals(source) || "---".equals(source)) {
                return -100;
            }
            if ("**".equals(source) || "***".equals(source)) {
                return -999;
            }
            return Integer.valueOf(source);
        }
    };


    @Data
    private static class People {
        private String name;
        private Integer age;
        private Long longAge;
    }


    @BeforeEach
    void before() {
        modelMapper.addConverter(toInteger);
    }

    @Test
    void test_Integer3() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "taro");
        map.put("age", "111");
        map.put("longAge", "0000000");
        // 変換
        People people = modelMapper.map(map, People.class);
        Assertions.assertEquals(people.getName(), "taro");
        Assertions.assertEquals(people.getAge(), 111);
        Assertions.assertEquals(people.getLongAge(), 0);
    }

    @Test
    void test_Integer2() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "taro");
        map.put("age", "**");
        // 変換
        People people = modelMapper.map(map, People.class);
        Assertions.assertEquals(people.getName(), "taro");
        Assertions.assertEquals(people.getAge(), -999);
    }


    @Test
    void test_Integer4() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "taro");
        map.put("age", "001");
        map.put("longAge", "    "); // Longは、スペースの場合、nullになる。
        // 変換
        People people = modelMapper.map(map, People.class);
        Assertions.assertEquals(people.getName(), "taro");
        Assertions.assertEquals(people.getAge(), 1);
        Assertions.assertEquals(people.getLongAge(), null);
    }

    @Test
    void test_Integer5() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "taro");
        map.put("age", "  ");
        // 変換
        People people = modelMapper.map(map, People.class);
        Assertions.assertEquals(people.getName(), "taro");
        Assertions.assertEquals(people.getAge(), null);
    }

    @Test
    void test_Integer6() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "taro");
        map.put("age", "---");
        // 変換
        People people = modelMapper.map(map, People.class);
        Assertions.assertEquals(people.getName(), "taro");
        Assertions.assertEquals(people.getAge(), -100);
    }

    @Test
    void test_Integer7() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "taro");
        map.put("age", "***");
        // 変換
        People people = modelMapper.map(map, People.class);
        Assertions.assertEquals(people.getName(), "taro");
        Assertions.assertEquals(people.getAge(), -999);
    }

}