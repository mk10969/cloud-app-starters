package org.uma.cloud.stream.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.uma.cloud.common.configuration.JvLinkDeserializer;
import org.uma.cloud.common.model.RacingDetails;
import org.uma.cloud.common.model.odds.Exacta;
import org.uma.cloud.common.model.odds.Quinella;
import org.uma.cloud.common.model.odds.QuinellaPlace;
import org.uma.cloud.common.model.odds.Trifecta;
import org.uma.cloud.common.model.odds.Trio;
import org.uma.cloud.common.model.odds.WinsPlaceBracketQuinella;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class JvLinkWebService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JvLinkDeserializer jvLinkDeserializer;

    // base url
    private static final String baseUrl = "http://192.168.56.104:8888";

    // レースID
    private static final String RACE_ID = "raceId";

    // 今週のレース情報
    private static final String racingDetailsThisWeek = "/racingDetails/thisWeek";

    // 単勝・複勝・枠連
    private static final String winsPlaceBracketQuinella = "/winsPlaceBracketQuinella";

    // 馬連
    private static final String quinella = "/quinella";

    // ワイド
    private static final String quinellaPlace = "/quinellaPlace";

    // 馬単
    private static final String exacta = "/exacta";

    // 三連複
    private static final String trio = "/trio";

    // 三連単
    private static final String trifecta = "/trifecta";

    // 馬連時系列
    private static final String timeseriesWinsPlaceBracketQuinella = "/timeseries/winsPlaceBracketQuinella";

    // 馬連時系列
    private static final String timeseriesQuinella = "/timeseries/quinella";


    private Mono<String> findOneByRaceId(String path, String raceId) {
        String body = restTemplate.getForObject(UriComponentsBuilder
                        .fromHttpUrl(baseUrl + path)
                        .queryParam(RACE_ID, raceId)
                        .toUriString(),
                String.class);

        return Mono.just(Optional.ofNullable(body).orElseThrow());

//        return webClient.method(HttpMethod.GET)
//                .uri(uriBuilder -> uriBuilder
//                        .path(path)
//                        .queryParam(RACE_ID, raceId)
//                        .build())
//                .retrieve()
//                .bodyToMono(String.class);
    }

    private Flux<String> findAllByRaceId(String path, String raceId) {
        String[] body = restTemplate.getForObject(UriComponentsBuilder
                        .fromHttpUrl(baseUrl + path)
                        .queryParam(RACE_ID, raceId)
                        .toUriString(),
                String[].class);

        return Flux.fromArray(Optional.ofNullable(body).orElseThrow());
    }


    public Flux<RacingDetails> raceDetailsThisWeek() {
        String[] body = restTemplate.getForObject(baseUrl + racingDetailsThisWeek, String[].class);

        return Flux.fromArray(Optional.ofNullable(body).orElseThrow())
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::racingDetailsFunction));
    }

    public Mono<WinsPlaceBracketQuinella> winsPlaceBracketQuinella(String raceId) {
        return findOneByRaceId(winsPlaceBracketQuinella, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::winsPlaceBracketQuinellaFunction));
    }

    public Mono<Quinella> quinella(String raceId) {
        return findOneByRaceId(quinella, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::quinellaFunction));
    }

    public Mono<QuinellaPlace> quinellaPlace(String raceId) {
        return findOneByRaceId(quinellaPlace, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::quinellaPlaceFunction));
    }

    public Mono<Exacta> exacta(String raceId) {
        return findOneByRaceId(exacta, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::exactaFunction));
    }

    public Mono<Trio> trio(String raceId) {
        return findOneByRaceId(trio, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::trioFunction));
    }

    public Mono<Trifecta> trifecta(String raceId) {
        return findOneByRaceId(trifecta, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::trifectaFunction));
    }

    public Flux<WinsPlaceBracketQuinella> timeseriesWinsPlaceBracketQuinella(String raceId) {
        return findAllByRaceId(timeseriesWinsPlaceBracketQuinella, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::winsPlaceBracketQuinellaFunction));
    }

    public Flux<Quinella> timeseriesQuinella(String raceId) {
        return findAllByRaceId(timeseriesQuinella, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::quinellaFunction));
    }

}
