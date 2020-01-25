package org.uma.platform.reactive.application.service;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class TestWebClient {

    private final WebClient webClient;

    @PostConstruct
    public void test() {
        System.out.println("post construct");
        webClient.get().uri("/book")
                .retrieve()
                .bodyToFlux(Book.class)
                .subscribe(System.out::println);
    }

    @Data
    private static class Book {
        private String isbn;
        private String title;
        private int price;
    }



}
