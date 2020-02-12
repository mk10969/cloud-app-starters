package org.uma.cloud.task;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties("task.ipatgo")
@Validated
public class IpatGoTaskProperties {

    /**
     * rest url
     */
    private String restUrl = "http://localhost:8787/";

    /**
     * ipatgo mode
     */
    private String mode = "deposit";

    /**
     * deposit money
     */
    private Integer money = 10000;



    public void setRestUrl(String restUrl) {
        this.restUrl = restUrl;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getRestUrl() {
        return this.restUrl;
    }

    public String getMode() {
        return this.mode;
    }
}
