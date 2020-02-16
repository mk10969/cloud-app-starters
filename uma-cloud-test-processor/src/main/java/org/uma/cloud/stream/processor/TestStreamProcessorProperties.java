package org.uma.cloud.stream.processor;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties("test.processor")
@Validated
public class TestStreamProcessorProperties {


    private String name;

    private Integer Number;

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(Integer number) {
        Number = number;
    }

    public String getName() {
        return this.name;
    }

    public Integer getNumber() {
        return this.Number;
    }
}
