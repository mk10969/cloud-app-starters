package org.uma.cloud.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JvLinkBatchProperties.class)
public class JvLinkBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(JvLinkBatchApplication.class, args);
    }

}
