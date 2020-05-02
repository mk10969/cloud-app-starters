package org.uma.cloud.common.test;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.uma.cloud.common.utils.lang.ModelUtil;

public class FieldExtendTest {

    @Getter
    @Setter
    public static class BaseModel {
        protected String name;
        private String name2;
    }

    @Getter
    @Setter
    public static class Person extends BaseModel {
        private String name;
        private String name2;
        private Integer age;
    }


    @Test
    void test() {
        Person person = new Person();
        person.setAge(1);
        person.setName("aaaa");
        person.setName2("tttt");

        System.out.println(ModelUtil.toJson(person));
    }

}
