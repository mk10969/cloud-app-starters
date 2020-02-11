package org.uma.cloud.task;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


@ConfigurationProperties("test.task")
@Validated
public class TestTaskProperties {

    /**
     * rest url  [host:port]
     */
    private String restUrl = "http://localhost:8787";

    /**
     * mode
     */
    private Mode mode = Mode.deposit;

    enum Mode {data, file, stat, history, deposit, withdraw}

    public String getRestUrl() {
        return this.restUrl;
    }

    public Mode getMode() {
        return this.mode;
    }

    public void setRestUrl(String restUrl) {
        this.restUrl = restUrl;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

}
