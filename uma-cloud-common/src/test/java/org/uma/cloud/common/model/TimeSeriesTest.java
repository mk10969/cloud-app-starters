package org.uma.cloud.common.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.uma.cloud.common.utils.lang.JacksonUtil;
import org.uma.cloud.common.utils.lang.ModelUtil;

import static org.junit.jupiter.api.Assertions.*;

class TimeSeriesTest {

    private final ObjectMapper objectMapper = JacksonUtil.getDefaultObjectMapper();

    @Test
    void test() {
        Model model = new Model();
        model.setName("aaa");
        // なるほど、getのアクセッサーをつけると、デシリアライズされるのか。。
        System.out.println(model);
    }

    private static class Model{
        @Getter
        @Setter
        private String name;

        public Integer timestamp() {
            return 1;
        }

        @Override
        public String toString() {
            return ModelUtil.toJson(this);
        }
    }
}