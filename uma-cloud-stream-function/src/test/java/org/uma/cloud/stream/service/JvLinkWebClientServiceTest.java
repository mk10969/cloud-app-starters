package org.uma.cloud.stream.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.uma.cloud.common.configuration.JvLinkDeserializer;
import reactor.core.publisher.Flux;


@SpringBootTest
class JvLinkWebClientServiceTest {

    @Autowired
    private JvLinkWebClientService jvLinkWebClientService;

    @Autowired
    private JvLinkDeserializer jvLinkDeserializer;

//    @Autowired
//    private WebClient webClient;

    @Autowired
    private RestTemplate restTemplate;


    private String[] findOne(String path, String raceId) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl("http://192.168.56.104:8888" + path)
                .queryParam("raceId", raceId);

        System.out.println(uriComponentsBuilder.toUriString());

        return restTemplate.getForObject(
                uriComponentsBuilder.toUriString(),
                String[].class);
    }


    @Test
    void test_restTemplate() {
        Flux.fromArray(findOne("/timeseries/quinella", "2020040509020411"))
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::quinellaFunction))
                .subscribe(System.out::println);
    }

//    @Test
//    void test_webClient() throws InterruptedException {
//        webClient.method(HttpMethod.GET)
//                .uri(uriBuilder -> uriBuilder
//                        .path("/timeseries/quinella")
//                        .queryParam("raceId", "2020040509020411")
//                        .build())
//                .retrieve()
//                .bodyToMono(new ParameterizedTypeReference<List<String>>() {
//                })
//                .subscribe();
//
//        Thread.sleep(3000L);
//    }


    @Test
    void test_timeseriesQuinella() throws InterruptedException {
        jvLinkWebClientService.timeseriesQuinella("2020040509020411")
                .subscribe(System.out::println);

        Thread.sleep(3000L);
    }

    @Test
    void test_timeseriesWinsPlaceBracketQuinella() throws InterruptedException {
        jvLinkWebClientService.timeseriesWinsPlaceBracketQuinella("2020040509020411")
                .subscribe(System.out::println);

        Thread.sleep(3000L);
    }

    @Test
    void test_findAllRaceDetailsThisWeek() throws InterruptedException {
        jvLinkWebClientService.raceDetailsThisWeek()
                .subscribe(System.out::println);

        Thread.sleep(3000L);
    }

}