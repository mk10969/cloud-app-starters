package org.uma.cloud.stream;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(StreamFunctionProperties.class)
public class StreamFunctionConfiguration {

}
