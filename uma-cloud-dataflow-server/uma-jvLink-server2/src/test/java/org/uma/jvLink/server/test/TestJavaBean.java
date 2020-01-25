package org.uma.jvLink.server.test;


import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;


@Data
public class TestJavaBean {

    private String id;

    private String name;

    private Integer price;

    private Integer amount;

    private LocalDate localDate;

    private List<String> company;

    private List<String> groups;

    //    @Enumerated(EnumType.STRING)
    @NotNull
    private TestEnum testEnum;

    public enum TestEnum {
        GOOD, BAD;
        TestEnum() {
        }
    }
}
