package org.uma.cloud.stream.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.uma.cloud.stream.StreamFunctionProperties;

@Configuration
public class WebClientConfiguration {

    @Autowired
    private StreamFunctionProperties properties;


    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(properties.getWebClientUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

}
