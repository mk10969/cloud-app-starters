//package org.uma.cloud.stream.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.web.reactive.function.client.WebClient;
//
//@Configuration
//public class WebClientConfiguration {
//
//    private static final String url = "http://192.168.56.104:8888";
//
//
//    @Bean
//    public WebClient webClient() {
//        return WebClient.builder()
//                .baseUrl(url)
//                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .build();
//    }
//
//}
