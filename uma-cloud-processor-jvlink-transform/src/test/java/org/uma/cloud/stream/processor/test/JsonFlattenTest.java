package org.uma.cloud.stream.processor.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wnameless.json.flattener.JsonFlattener;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonFlattenTest {

    private String toJson(Object model) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(model);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("jsonに変換できませんでした。");
        }
    }

    @Data
    private static class Message {
        private String id;
        private Integer number;
        private List<String> context;

        public Message(String id, Integer number, List<String> context) {
            this.id = id;
            this.number = number;
            this.context = context;
        }
    }


    @Test
    void test__() {

        List<String> context = new ArrayList<>();
        context.add("aaa");
        context.add(null);
        context.add("ccc");
        Message message = new Message("111", 123, context);

        String json = toJson(message);
        System.out.println(json);
        Map<String, Object> jsonMap = JsonFlattener.flattenAsMap(json);
        System.out.println(jsonMap);

        jsonMap.entrySet().stream()
                .filter(e -> e.getValue() == null)
                .forEach(System.out::println);


    }


}
