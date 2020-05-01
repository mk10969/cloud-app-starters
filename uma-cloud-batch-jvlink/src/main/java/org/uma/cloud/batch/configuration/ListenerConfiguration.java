package org.uma.cloud.batch.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.batch.listener.JvLinkSkipPolicy;
import org.uma.cloud.batch.listener.JvLinkStepExecutionListener;

@Configuration
public class ListenerConfiguration {

    @Bean
    public JvLinkStepExecutionListener stepExecutionListener() {
        return new JvLinkStepExecutionListener();
    }

    @Bean
    public JvLinkSkipPolicy jvLinkSkipPolicy() {
        return new JvLinkSkipPolicy();
    }
}
