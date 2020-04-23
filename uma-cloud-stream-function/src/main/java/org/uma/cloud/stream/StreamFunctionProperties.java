package org.uma.cloud.stream;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties("uma.stream.function")
@Validated
public class StreamFunctionProperties {

    @Getter
    @Setter
    private boolean debug = false;

    @Getter
    @Setter
    private String InfluxDBUrl = "http://localhost:8086";

    @Getter
    @Setter
    private String InfluxDBDatabase = "uma";

    @Getter
    @Setter
    private String JvLinkWebUrl = "http://192.168.56.104:8888";

    @Getter
    @Setter
    private String JvLinkWebSocketUrl = "ws://192.168.56.104:8888/jvWatchEvent";
}
