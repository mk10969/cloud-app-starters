package org.uma.cloud.common.utils.lang;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.StandardSystemProperty;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

class JacksonUtilTest {

    private static final DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    private static final DateTimeFormatter HHmmssSSS = DateTimeFormatter.ofPattern("mm:ss.SSS");


    @Test
    void test_() {
        System.out.println(LocalDateTime.now().format(yyyyMMdd));
        System.out.println(LocalDateTime.now().format(HHmmssSSS));
    }

    @Test
    void test_long() {
        // spaceは、nullに変換されるのか。。convertorを設定するとそっち優先か。。なるほど
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> map = new HashMap<>();
        map.put("numberLong", "       ");
        map.put("numberIntger", "       ");
        Once once = objectMapper.convertValue(map, Once.class);
        System.out.println(once.getNumberLong());
        System.out.println(once.getNumberIntger());

    }

    @Getter
    public static class Once {
        private Long numberLong;
        private Integer numberIntger;

    }



}