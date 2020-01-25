package org.uma.platform.reactive.application.configuration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class WebClientConfiguration {

    @Value("${service.baseUrl}")
    private String baseUrl;


    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(this.baseUrl)
                .defaultHeader(MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

}
