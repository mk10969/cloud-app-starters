package org.uma.cloud.stream.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.uma.cloud.common.utils.lang.JacksonUtil;
import org.uma.cloud.common.utils.lang.ModelUtil;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class JacksonTest {

    private final ObjectMapper objectMapper = JacksonUtil.getDefaultObjectMapper();

    @Test
    void test_小数点の出力() {
        Map<String, BigDecimal> map = new HashMap<>();
        map.put("number", new BigDecimal("1.1"));
        Model model = objectMapper.convertValue(map, Model.class);
        System.out.println(model);
    }

    @Test
    void test_小数点の出力2() {
        Map<String, BigDecimal> map = new HashMap<>();
        map.put("number", new BigDecimal("00.0"));
        Model model = objectMapper.convertValue(map, Model.class);
        System.out.println(model);
    }


    @Getter
    private static class Model {

        private BigDecimal number;

        @Override
        public String toString() {
            return ModelUtil.toJson(this);
        }
    }

}
