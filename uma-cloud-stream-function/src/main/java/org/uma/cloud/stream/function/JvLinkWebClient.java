package org.uma.cloud.stream.function;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
public class JvLinkWebClient {

    @Autowired
    private WebClient webClient;


    public Supplier<Flux<String>> hhtpTimeseries() {
        return () -> webClient.method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder
                        .path("/timeseries/quinella")
                        .queryParam("raceId", "raceId")
                        .build())
                .retrieve()
                .bodyToMono(String[].class)
                .flatMapMany(Flux::fromArray);
    }


    public void timeseriesFlux() {
//        String raceId = "2020032009010911";
        String raceId = "2020031506010911";

        webClient.method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder
                        .path("/timeseries/quinella")
                        .queryParam("raceId", raceId)
                        .build())
                .retrieve()
                .bodyToFlux(String.class)
                .subscribe(
                        System.out::println,
                        System.out::println,
                        () -> System.out.println("完了")
                );
    }


//    public void timeseriesMono() {
////        String raceId = "2020032009010911";
//        String raceId = "2020031506010911";
//
//        webClient.method(HttpMethod.GET).
//                uri(uriBuilder -> uriBuilder
//                        .path("/timeseries/quinella")
//                        .queryParam("raceId", raceId)
//                        .build())
//                .retrieve()
//                .bodyToMono(String[].class)
//                .flatMapMany(Flux::fromArray)
//                .map(jvLinkFunction.decode().andThen(jvLinkFunction::quinellaFunction))
//                .subscribe(
//                        System.out::println,
//                        System.out::println,
//                        () -> System.out.println("完了")
//                );
//    }
//
//
//    public Flux<Quinella> timeSeriesQuinella(String raceId) {
//        return webClient.method(HttpMethod.GET).
//                uri(uriBuilder -> uriBuilder
//                        .path("/timeseries/quinella")
//                        .queryParam("raceId", raceId)
//                        .build())
//                .retrieve()
//                .bodyToMono(String[].class)
//                .flatMapMany(Flux::fromArray)
//                .map(jvLinkFunction.decode().andThen(jvLinkFunction::quinellaFunction));
//    }
//
//    public Flux<WinsPlaceBracketQuinella> timeSeriesWinsPlaceBracketQuinella(String raceId) {
//        return webClient.method(HttpMethod.GET).
//                uri(uriBuilder -> uriBuilder
//                        .path("/timeseries/winsPlaceBracketQuinella")
//                        .queryParam("raceId", raceId)
//                        .build())
//                .retrieve()
//                .bodyToMono(String[].class)
//                .flatMapMany(Flux::fromArray)
//                .map(jvLinkFunction.decode().andThen(jvLinkFunction::winsPlaceBracketQuinellaFunction));
//    }

}
