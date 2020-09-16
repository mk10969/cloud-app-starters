package org.uma.cloud.common.utils.lang;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.uma.cloud.common.entity.BaseModel;

import java.io.IOException;
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
        objectMapper.registerModule(new SimpleModule().addDeserializer(Integer.class, new IntegerDeserializer()));

        Map<String, String> map = new HashMap<>();
        map.put("numberLong", "       ");
        map.put("numberIntger", "11111");
        Once once = objectMapper.convertValue(map, Once.class);
        System.out.println(once.getNumberLong());
        System.out.println(once.getNumberIntger());

    }

    @Test
    void test_フィールド名とjsonが一致しないとき() {
        ObjectMapper objectMapper = JacksonUtil.getJvLinkObjectMapper();
        Map<String, Number> map = new HashMap<>();
        map.put("numberLong", 111);
        map.put("numberIntger", 222222);
        map.put("value", 3333);
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                objectMapper.convertValue(map, Once.class));
    }

    @Test
    void test_Jsonプロパティが欠損しているとき() {
        ObjectMapper objectMapper = JacksonUtil.getJvLinkObjectMapper();
        Map<String, Object> map = new HashMap<>();
        map.put("recordType", "BN");
        map.put("dataDiv", "1");
        map.put("dataCreateDate", "20201111");
        map.put("numberLong", 111);
        Once once = objectMapper.convertValue(map, Once.class);
        ModelUtil.fieldNotNull(once);
    }


    @Getter
    public static class Once extends BaseModel {
        private Long numberLong;
        private Integer numberIntger;

    }


    private static final class IntegerDeserializer extends JsonDeserializer<Integer> {
        @Override
        public Integer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            // field name とれるのね！
            System.out.println("getCurrentName: " + p.getCurrentName());
            String source = p.getValueAsString();
            return Integer.valueOf(source);
        }
    }

    @Test
    void test_Boolean() {
        System.out.println(Boolean.valueOf("1"));
        System.out.println(Boolean.valueOf("0"));

    }

}