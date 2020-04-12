package org.uma.cloud.stream.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import org.uma.cloud.common.configuration.JvLinkDeserializer;
import org.uma.cloud.stream.SkipCommandLineRunnerTestConfiguration;


@SpringBootTest(classes = SkipCommandLineRunnerTestConfiguration.class)
class JvLinkWebServiceTest {

    @Autowired
    private JvLinkWebService jvLinkWebService;

    @Autowired
    private JvLinkDeserializer jvLinkDeserializer;

    @Autowired
    private WebClient webClient;

//    @Autowired
//    private RestTemplate restTemplate;


    @Getter
    @ToString
    protected static class ExternalResponse {

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
        jvLinkWebService.raceDetailsThisWeek()
                .subscribe(System.out::println);

        Thread.sleep(3000L);
    }

}