package org.uma.cloud.common.test;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.uma.cloud.common.utils.lang.StringUtil;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestToJson {

    @Getter
    @Setter
    static class Base {
        private String name;
        private Integer age;

        @Override
        public String toString() {
            return StringUtil.toJson(this);
        }

    }

    @Getter
    @Setter
    static class User extends Base {
        private String address;
        private List<Hobby> hobby;

        @Data
        static class Hobby {
            private String name;
        }
    }


    @Test
    void test() {
        User user = new User();
        user.setName("user");
        user.setAge(10);
        user.setAddress(null);
        User.Hobby hobby = new User.Hobby();
        hobby.setName("baseball");
        user.setHobby(Collections.singletonList(hobby));

        User user2 = new User();
        user2.setName("user2");
        user2.setAge(20);
        user2.setAddress(null);
        User.Hobby hobby2 = new User.Hobby();
        hobby2.setName("basketball");
        user2.setHobby(Collections.singletonList(hobby2));

        assertEquals("{\"address\":null,\"hobby\":[{\"name\":\"baseball\"}],\"name\":\"user\",\"age\":10}",
                user.toString());
        assertEquals("{\"address\":null,\"hobby\":[{\"name\":\"basketball\"}],\"name\":\"user2\",\"age\":20}",
                user2.toString());
    }

}
