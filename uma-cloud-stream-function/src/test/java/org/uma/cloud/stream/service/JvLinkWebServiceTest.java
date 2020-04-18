package org.uma.cloud.stream.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.reactive.function.client.WebClient;
import org.uma.cloud.common.configuration.JvLinkDeserializer;
import org.uma.cloud.stream.StreamFunctionProperties;
import org.uma.cloud.stream.configuration.WebClientConfiguration;

@Slf4j
@SpringBootTest(classes = {
        WebClientConfiguration.class,
        StreamFunctionProperties.class,
        JvLinkWebService.class,
        JvLinkWebServiceTest.DefaultTestConfiguration.class,
})
class JvLinkWebServiceTest {

    @Configuration
    @EnableConfigurationProperties
    @TestPropertySource(value = "classpath:JvLinkRecord.properties")
    @ComponentScan(basePackages = "org.uma.cloud.common.configuration")
    public static class DefaultTestConfiguration {
    }


    @Autowired
    private JvLinkWebService jvLinkWebService;

    @Autowired
    private JvLinkDeserializer jvLinkDeserializer;

    @Autowired
    private WebClient webClient;


    @Getter
    @ToString
    static class ExternalResponse {

        private final String data;

        private final String message;

        @JsonCreator
        public ExternalResponse(
                @JsonProperty("data") String data,
                @JsonProperty("message") String message) {
            this.data = data;
            this.message = message;
        }
    }


    @Test
    void test_webClient_raceDetails() throws InterruptedException {
        String WEDNESDAY = "1586886877189";
        String FRIDAY = "1587059893206";

        webClient.get().uri(uriBuilder -> uriBuilder
                .path("/racingDetails/1587059893206")
                .build())
                .retrieve()
                .bodyToFlux(ExternalResponse.class)
                .map(ExternalResponse::getData)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::racingDetailsFunction))
                .subscribe(
                        System.out::println,
                        System.out::println,
                        () -> System.out.println("完了")
                );
        Thread.sleep(5000L);
    }


    @Test
    void test_webClient_thisWeek() throws InterruptedException {
        webClient.get().uri(uriBuilder -> uriBuilder
                .path("/racingDetails/thisWeek")
                .build())
                .retrieve()
                .bodyToFlux(ExternalResponse.class)
                .map(ExternalResponse::getData)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::racingDetailsFunction))
                .doOnError(throwable -> {
                    if (throwable instanceof WebClientConfiguration.JvLinkWebClientException) {
                        if (HttpStatus.NOT_FOUND == ((WebClientConfiguration.JvLinkWebClientException) throwable).getHttpStatus()) {
                            log.warn("データなし: {}", throwable);
                        } else {
                            // リクエスト形式に誤りあり。
                            log.error("クライアントのリクエストに問題あり : ", throwable);
                        }
                    } else {
                        // サーバ側に問題あり。
                        log.error("JvLinkWebServiceでエラー発生: ", throwable);
                    }
                })
                .subscribe(
                        System.out::println,
                        System.out::println,
                        () -> System.out.println("完了")
                );
        Thread.sleep(5000L);
    }


    @Test
    void test_webClient_timeSeries() throws InterruptedException {
        webClient.get().uri(uriBuilder -> uriBuilder
                .path("/timeseries/quinella")
                .queryParam("raceId", "2020040509020411")
                .build())
                .retrieve()
                .bodyToFlux(ExternalResponse.class)
                .map(ExternalResponse::getData)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::quinellaFunction))
                .subscribe(
                        System.out::println,
                        System.out::println,
                        () -> System.out.println("完了")
                );
        Thread.sleep(3000L);
    }

    @Test
    void test_webClient() throws InterruptedException {
        webClient.get().uri(uriBuilder -> uriBuilder
                .path("/racingDetails")
                .queryParam("raceId", "2020040509020411")
                .build())
                .retrieve()
                .bodyToMono(ExternalResponse.class)
                .map(ExternalResponse::getData)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::racingDetailsFunction))
                .subscribe(
                        System.out::println,
                        System.out::println,
                        () -> System.out.println("完了")
                );
        Thread.sleep(3000L);
    }


    @Test
    void test_timeseriesQuinella() throws InterruptedException {
        jvLinkWebService.timeseriesQuinella("2020040509020411")
                .subscribe(System.out::println);

        Thread.sleep(3000L);
    }

    @Test
    void test_timeseriesWinsPlaceBracketQuinella() throws InterruptedException {
        jvLinkWebService.timeseriesWinsPlaceBracketQuinella("2020040509020411")
                .subscribe(System.out::println);

        Thread.sleep(3000L);
    }

    @Test
    void test_findAllRaceDetailsThisWeek() throws InterruptedException {
        jvLinkWebService.raceDetailsWithFriday()
                .subscribe(System.out::println);
        Thread.sleep(5000L);
    }




//    @Test
//    void test_error_handle() {
//        webClient.get()
//                .uri("/")
//                .exchange()
//                .flatMap(clientResponse -> {
//                     if (clientResponse.statusCode().is4xxClientError()) {
//                         // ...
//                     }
//                     // ...
//                    clientResponse.bodyToMono(ExampleResponse.class);
//                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError, i -> Mono.just(new RuntimeException()))
//                .onStatus(HttpStatus::is5xxServerError, i -> Mono.just(new RuntimeException()))
//                .bodyToMono(MyPojo.class);
//    }


    /**
     * ボツ
     */
//    @Autowired
//    private RestTemplate restTemplate;


    //    private String[] findOne(String path, String raceId) {
//        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
//                .fromHttpUrl("http://192.168.56.104:8888" + path)
//                .queryParam("raceId", raceId);
//
//        System.out.println(uriComponentsBuilder.toUriString());
//
//        return restTemplate.getForObject(
//                uriComponentsBuilder.toUriString(),
//                String[].class);
//    }
//
//
//    @Test
//    void test_restTemplate() {
//        Flux.fromArray(findOne("/timeseries/quinella", "2020040509020411"))
//                .map(jvLinkDeserializer.decode()
//                        .andThen(jvLinkDeserializer::quinellaFunction))
//                .subscribe(System.out::println);
//    }


    //    private Mono<String> findOneByRaceId(String path, String raceId) {
//        String body = restTemplate.getForObject(UriComponentsBuilder
//                        .fromHttpUrl(baseUrl + path)
//                        .queryParam(RACE_ID, raceId)
//                        .toUriString(),
//                String.class);
//
//        return Mono.just(Optional.ofNullable(body).orElseThrow());
//    }

//    private Flux<String> findAllByRaceId(String path, String raceId) {
//        String[] body = restTemplate.getForObject(UriComponentsBuilder
//                        .fromHttpUrl(baseUrl + path)
//                        .queryParam(RACE_ID, raceId)
//                        .toUriString(),
//                String[].class);
//
//        return Flux.fromArray(Optional.ofNullable(body).orElseThrow());
//    }


}