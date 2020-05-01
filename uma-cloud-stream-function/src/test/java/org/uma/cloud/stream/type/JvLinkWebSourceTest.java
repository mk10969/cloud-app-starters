package org.uma.cloud.stream.type;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.uma.cloud.common.utils.lang.DateUtil;
import org.uma.cloud.stream.StreamFunctionProperties;
import org.uma.cloud.stream.configuration.WebClientConfiguration;

import java.time.LocalDateTime;

@Slf4j
@SpringBootTest(classes = {
        WebClientConfiguration.class,
        StreamFunctionProperties.class,
        JvLinkWebSource.class,
        JvLinkWebSourceTest.DefaultTestConfiguration.class,
})
class JvLinkWebSourceTest {

    @Configuration
    @EnableConfigurationProperties
    @TestPropertySource(value = "classpath:JvLinkRecord.properties")
    @ComponentScan(basePackages = "org.uma.cloud.common.configuration")
    public static class DefaultTestConfiguration {
    }


    @Autowired
    private JvLinkWebSource jvLinkWebSource;


    @Test
    void test_DateTime() {
        System.out.println(DateUtil.toEpochMilli(LocalDateTime.now().minusWeeks(1)));
    }


    @Test
    void test_timeseriesQuinella() throws InterruptedException {
        jvLinkWebSource.timeseriesQuinella("2020040509020411")
                .subscribe(System.out::println);

        Thread.sleep(3000L);
    }

    @Test
    void test_timeseriesWinsPlaceBracketQuinella() throws InterruptedException {
        jvLinkWebSource.timeseriesWinsShowBracketQ("2020040509020411")
                .subscribe(System.out::println);

        Thread.sleep(3000L);
    }

    @Test
    void test_findAllRaceDetailsThisWeek() throws InterruptedException {
        jvLinkWebSource.storeRacingDetail()
                .subscribe(System.out::println);
        Thread.sleep(5000L);
    }


    @Test
    void test_findOneRaceDetails() throws InterruptedException {
        jvLinkWebSource.realtimeRacingDetail("2020042605020212")
                .subscribe(System.out::println);
        Thread.sleep(5000L);
    }


    @Test
    void test_findAllHorseRacingDetails() throws InterruptedException {
        jvLinkWebSource.realtimeRacingHorseDetail("2020042605020212")
                .subscribe(System.out::println);
        Thread.sleep(5000L);
    }


    @Test
    void test_event_weight() throws InterruptedException {
        // OK
        jvLinkWebSource.eventWeight("202004260510")
                .subscribe(System.out::println);
        Thread.sleep(10000L);
    }


    @Test
    void test_event_racingRefund() throws InterruptedException {
        // OK
        jvLinkWebSource.eventRacingRefund("202004250510")
                .subscribe(System.out::println);
        Thread.sleep(10000L);
    }


    @Test
    void test_event_eventWeather() throws InterruptedException {
        // OK
        jvLinkWebSource.eventWeather("WE20200426080020200426131500")
                .subscribe(System.out::println);
        Thread.sleep(10000L);
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