package org.uma.cloud.stream.util;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BusinessMapperTest {

    @Test
    void test() {
        assertEquals(1, 1);
    }

    @Test
    void jackson_modelmapperやってみる() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper()
                // この設定を、falseにすれば、きれいにいけるか。。
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        BeforeMode beforeMode = new BeforeMode();
        beforeMode.setName("aaa");
        beforeMode.setNumber(111);
        beforeMode.setSex(BeforeMode.Sex.MAN);
        beforeMode.setAddress("japan");
        beforeMode.setValues(Arrays.asList(1, 2, 3, 4, 5));

        JsonNode beforeJsonNode = objectMapper.valueToTree(beforeMode);
        AfterModel afterModel = objectMapper.treeToValue(beforeJsonNode, AfterModel.class);
        System.out.println(afterModel);
    }


    @Data
    public static class BeforeMode {
        private String name;
        private Integer number;
        private Sex sex;
        private String address;
        private List<Integer> values;

        public enum Sex {
            MAN(0),
            WOMAN(1),
            ;
            private int code;

            Sex(int code) {
                this.code = code;
            }

            @JsonValue
            public int getCode() {
                return this.code;
            }
        }
    }

    @Data
    public static class AfterModel {
        private String name;
        private Integer sex;
        private List<Integer> values;
    }
}