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

}
