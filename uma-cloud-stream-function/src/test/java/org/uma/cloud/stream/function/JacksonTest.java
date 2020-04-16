package org.uma.cloud.stream.function;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.junit.jupiter.api.Test;

public class JacksonTest {


    @Test
    void test() {

        ObjectMapper objectMapper = new ObjectMapper();

        String json = "{ \"color\": \"gold\"}";


        try {
            objectMapper.readValue(json, Once.class);

        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }


    @Data
    static class Once {

        private String name;

        private Integer age;

    }

}
